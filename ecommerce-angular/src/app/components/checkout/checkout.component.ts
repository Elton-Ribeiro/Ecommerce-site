import { Component, ElementRef, OnInit, ViewChild } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { CardToken, Identifications, Issuers, PaymentMethod } from 'src/app/interface/mercadopago';

declare const mercadopago: any;

@Component({
  selector: 'app-checkout',
  templateUrl: './checkout.component.html',
  styleUrls: [ './checkout.component.scss' ]
})

export class CheckoutComponent implements OnInit {

  minDocNumber = 5;
  maxDocNumber = 20;
  typeDocNumber = 'string';
  paymentForm: FormGroup = this.fb.group({
    cardNumber: [
      '',
      [
        Validators.required,
        Validators.pattern('^[ 0-9]*$'),
        Validators.minLength(17),
      ],
    ],
    securityCode: [ '', [ Validators.required, Validators.minLength(3) ] ],
    cardExpirationMonth: [ '', [ Validators.required, Validators.minLength(2) ] ],
    cardExpirationYear: [ '', [ Validators.required, Validators.minLength(2) ] ],
    cardholderName: [ '', Validators.required ],
    docType: [ '', [ Validators.required ] ],
    docNumber: [
      '',
      [
        Validators.required,
        Validators.minLength(this.minDocNumber),
        Validators.maxLength(this.maxDocNumber),
      ],
    ],
  });
  publicKey = 'TEST-20ea027b-79b0-42de-beb1-222d2ca37d25';
  typesNeeded!: boolean;
  identifications!: Identifications[];
  @ViewChild('ccNumber')
  ccNumberField!: ElementRef;
  @ViewChild('formContainer')
  formRef!: ElementRef;


  constructor(private fb: FormBuilder) { }

  ngOnInit(): void { }

  ngAfterViewInit(): void {
    this.appendScript().then(() => {
      mercadopago.setPublishableKey(this.publicKey);
      this.getTypes();
    });
  }

  appendScript(): Promise<any> {
    return new Promise((resolve) => {
      const scriptTag = document.createElement('script');
      scriptTag.src =
      'https://sdk.mercadopago.com/js/v2';
      scriptTag.onload = resolve;
      document.body.appendChild(scriptTag);
    });
  }

  getTypes(): void {
    mercadopago.getIdentificationTypes(
      (err: number, data: Identifications[]) => {
        if (err !== 200) {
          throw err;
        }
        this.identifications = data;
        this.typesNeeded = true;
      }
    );
  }

  validateNumber(event: { charCode: number; preventDefault: () => void; }): void {
    const pattern = /[0-9]/;
    const inputChar = String.fromCharCode(event.charCode);
    if (!pattern.test(inputChar)) {
      event.preventDefault();
    }
  }

  creditCardNumberSpacing(): void {
    const input = this.ccNumberField.nativeElement;
    const { selectionStart } = input;
    const { cardNumber } = this.paymentForm.controls;

    let trimmedCardNum = cardNumber.value.replace(/\s+/g, '');
    if (trimmedCardNum.length >= 6) {
      const str = trimmedCardNum.substring(0, 6);
      const bin = { bin: str };
      this.callGetPaymentMethod(bin);
    }
    if (trimmedCardNum.length > 16) {
      trimmedCardNum = trimmedCardNum.substr(0, 16);
    }

    /* Handle American Express 4-6-5 spacing */
    const partitions =
      trimmedCardNum.startsWith('34') || trimmedCardNum.startsWith('37')
        ? [ 4, 6, 5 ]
        : [ 4, 4, 4, 4 ];

    const numbers: any[] = [];
    let position = 0;
    partitions.forEach((partition) => {
      const part = trimmedCardNum.substr(position, partition);
      if (part) {
        numbers.push(part);
      }
      position += partition;
    });

    cardNumber.setValue(numbers.join(' '));

    /* Handle caret position if user edits the number later */
    if (selectionStart < cardNumber.value.length - 1) {
      input.setSelectionRange(selectionStart, selectionStart, 'none');
    }
  }

  callGetPaymentMethod(binObj: { bin: any; }): void {
    mercadopago.getPaymentMethod(
      binObj,
      (err: number, handler: PaymentMethod[]) => {
        if (err !== 200) {
          throw err;
        }
        console.log('PaymentMethod', handler);
        const cardHandler = handler[ 0 ];
        this.getIssuers(cardHandler.id);
      }
    );
  }

  selectDocType(): void {
    // tslint:disable-next-line: no-string-literal
    const optionSelected = this.paymentForm.controls[ 'docType' ].value;
    const typeDoc = this.identifications.filter(
      (element) => element.id === optionSelected
    );
    const { min_length, max_length, type } = typeDoc[ 0 ];
    this.minDocNumber = min_length;
    this.maxDocNumber = max_length;
    this.typeDocNumber = type;
    // tslint:disable-next-line: no-string-literal
    this.paymentForm.controls[ 'docNumber' ].setValue('');
  }

  validateDocNumber(event: { charCode: number; preventDefault: () => void; }): void {
    if (this.typeDocNumber === 'number') {
      const pattern = /[0-9]/;
      const inputChar = String.fromCharCode(event.charCode);
      if (!pattern.test(inputChar)) {
        event.preventDefault();
      }
    }
  }

  getIssuers(id: string): void {
    mercadopago.getIssuers(id, (err: number, res: Issuers[]) => {
      if (err !== 200) {
        console.error(err);
        throw err;
      }
      console.log('Issuers', res);
    });
  }

  submit(): void {
    mercadopago.createToken(
      this.formRef.nativeElement,
      (err: number, res: CardToken) => {
        if (err !== 200) {
          console.error(err);
          throw err;
        }
        console.log('CardToken', res);
      }
    );
  }


}
