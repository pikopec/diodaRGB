/**
 * Kable:
 * VCC, GND
 * P1.1 Bluetooth (Procek RX - Bt TX)
 * P2.1 Blue
 * P2.4 Green
 * P1.6 Red
 *
 *
 *
 */

#include <msp430.h>

#define RED_DUTY 	TA0CCR1
#define GREEN_DUTY 	TA1CCR2
#define BLUE_DUTY 	TA1CCR1

volatile char color[3] = {0x00, 0x00, 0x00};
int btcounter = 0;

void init(){
	/*** Disable Watchdog timer ***/
    WDTCTL = WDTPW | WDTHOLD; 	// Stop Watchdog timer
    BCSCTL1 = CALBC1_16MHZ; 	// Select clock settings for 16MHz
    DCOCTL = CALDCO_16MHZ;		// -||-

    /*** PWM GPIO setup ***/
    P2DIR |= BIT0 | BIT1 | BIT4; // P2.1, P2.4 sets as outputs
    P2SEL |= BIT0 | BIT1 | BIT4; // Out1, Out2 from Timer A1 comparators
    P1DIR |= BIT6 | BIT2; // P1.6 set as output
    P1SEL |= BIT6; // Out1 from Timer A0 comparator


    /*** Timer A0 setup ***/
	TA0CCTL1 |= OUTMOD_7;			// PWM output mode: 3 - PWM set/reset (ew output 7 reset/set)
	TA0CTL |= TASSEL_2 + MC_2;		// Clock: SMCLK, Continuous mode

    /*** Timer A1 setup ***/
	TA1CCTL1 |= OUTMOD_7;			// PWM output mode: 3 - PWM set/reset (ew output 7 reset/set)
	TA1CCTL2 |= OUTMOD_7;			// PWM output mode: 3 - PWM set/reset (ew output 7 reset/set)
	TA1CTL |= TASSEL_2 + MC_2;		// Clock: SMCLK, Continuous mode

	/*** LEDs initial power ***/
	RED_DUTY = 0xfff0;				// Initialize LEDs with very weak light
	GREEN_DUTY = 0xfff0;			// Initialize LEDs with very weak light
	BLUE_DUTY = 0xfff0;				// Initialize LEDs with very weak light


	/*** UART module initialization ***/
	P1DIR |= BIT2;
    P1SEL |= BIT1 | BIT2 ;
    P1SEL2 |= BIT1 | BIT2;
    UCA0CTL1 = UCSWRST;
    UCA0CTL1 |= UCSSEL_2;
    UCA0BR0 = 65;
    UCA0BR1 = 3;
    UCA0MCTL = UCBRS1 + UCBRS0;
    UCA0CTL1 &= ~UCSWRST;
    IE2 |= UCA0RXIE;
    __bis_SR_register(GIE);

    P1OUT &= 0xFF;
    P2OUT &= 0xFF;
}


int main(void) {
    init();


    while(1){
    	/*** Setting PWM Duty to each color according to received values ***/
    	RED_DUTY = ~((color[0]<<8)|(color[0]));		//scaling range 0x00-0xff to 0x0000-0xffff
    	GREEN_DUTY = ~((color[1]<<8)|(color[1]));	//				char size	 compare register size
    	BLUE_DUTY = ~((color[2]<<8)|(color[2]));
    }


    return 0;	// Unreachable but main should return int.
}

#pragma vector=USCIAB0RX_VECTOR
__interrupt void USCI0RX_ISR(void){

	/*** reading bytes from bluetooth to color array (in order R,G,B) **/
    color[btcounter] = UCA0RXBUF;
    btcounter++;
    if(btcounter == 3) btcounter = 0; // reseting array counter after each message
    // message sent by app is 3 char long

}


