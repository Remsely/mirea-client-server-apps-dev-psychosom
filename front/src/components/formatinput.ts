document.addEventListener("DOMContentLoaded", function () {
    const nameInputs = document.querySelectorAll<HTMLInputElement>('input[type="name"]');

    nameInputs.forEach(input => {
        input.addEventListener('input', function (event: Event) {
            const target = event.target as HTMLInputElement;

            target.value = target.value.replace(/[^a-zA-Zа-яА-Я]/g, '');
        });
    });

    const phoneInputs = document.querySelectorAll<HTMLInputElement>('input[type="tel"]');

    const getInputNumbersValue = (input: HTMLInputElement): string => {
        return input.value.replace(/\D/g, '');
    };

    const onPhonePaste = (e: ClipboardEvent): void => {
        const input = e.target as HTMLInputElement;
        const inputNumbersValue = getInputNumbersValue(input);

        if (e.clipboardData) {
            const pastedText = e.clipboardData.getData('text');
            if (/\D/g.test(pastedText)) {
                input.value = inputNumbersValue;
                return;
            }
        } else if ((window as Window & typeof globalThis & { clipboardData?: DataTransfer }).clipboardData) {

            const pastedText = (window as Window & typeof globalThis & { clipboardData?: DataTransfer }).clipboardData?.getData('Text');
            if (pastedText && /\D/g.test(pastedText)) {
                input.value = inputNumbersValue;
                return;
            }
        }
    };

    const onPhoneInput = (e: InputEvent): void => {
        const input = e.target as HTMLInputElement;
        let inputNumbersValue = getInputNumbersValue(input);
        const selectionStart = input.selectionStart;
        let formattedInputValue = '';

        if (!inputNumbersValue) {
            input.value = '';
            return;
        }

        if (input.value.length !== selectionStart) {
            if (e.data && /\D/g.test(e.data)) {
                input.value = inputNumbersValue;
            }
            return;
        }

        if (['7', '8', '9'].indexOf(inputNumbersValue[0]) > -1) {
            if (inputNumbersValue[0] === '9') inputNumbersValue = '7' + inputNumbersValue;
            const firstSymbols = (inputNumbersValue[0] === '8') ? '8' : '+7';
            formattedInputValue = input.value = firstSymbols + ' ';
            if (inputNumbersValue.length > 1) {
                formattedInputValue += '(' + inputNumbersValue.substring(1, 4);
            }
            if (inputNumbersValue.length >= 5) {
                formattedInputValue += ') ' + inputNumbersValue.substring(4, 7);
            }
            if (inputNumbersValue.length >= 8) {
                formattedInputValue += '-' + inputNumbersValue.substring(7, 9);
            }
            if (inputNumbersValue.length >= 10) {
                formattedInputValue += '-' + inputNumbersValue.substring(9, 11);
            }
        } else {
            formattedInputValue = '+' + inputNumbersValue.substring(0, 16);
        }
        input.value = formattedInputValue;
    };

    const onPhoneKeyDown = (e: KeyboardEvent): void => {
        const input = e.target as HTMLInputElement;
        const inputValue = input.value.replace(/\D/g, '');
        if (e.key === 'Backspace' && inputValue.length === 1) {
            input.value = '';
        }
    };

    for (const phoneInput of phoneInputs) {
        phoneInput.addEventListener('keydown', onPhoneKeyDown);
        phoneInput.addEventListener('input', onPhoneInput as EventListener, false);
        phoneInput.addEventListener('paste', onPhonePaste, false);
    }
});