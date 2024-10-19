import {FieldError, FieldValues, SubmitHandler, useForm} from "react-hook-form";
import {useState} from "react";
import styles from "./ConsultationForm.module.scss";
import ContactInputForm from "./ContactInputForm/ContactInputForm.tsx";
import NameInputForm from "./NameInputForm/NameInputForm.tsx";
import TextareaForm from "./TextareaForm/TextareaForm.tsx";
import ButtonMaster from "../ButtonMaster/ButtonMaster.tsx";

interface ConsultationFormProps {
    setIsOpen: (isOpen: boolean) => void;
}

export default function ConsultationForm({setIsOpen}: ConsultationFormProps) {
    const { register, handleSubmit, reset, formState: { errors } } = useForm({
        mode: "onChange",
    });

    const [isTelegram, setIsTelegram] = useState<boolean>(false);
    const [contactValue, setContactValue] = useState<string>("");

    const onSubmit: SubmitHandler<FieldValues> = (data: object) => {
        console.log(data);
        setIsOpen(true);
        reset();
        setContactValue("");
    };

    return (
        <form onSubmit={handleSubmit(onSubmit)} method="POST" className={`${styles.form} container`}>
            <div className={`${styles.inputs} block`}>
                <NameInputForm
                    label="Имя"
                    name="firstname"
                    register={register}
                    errors={errors as Record<string, FieldError | undefined>}
                />
                <NameInputForm
                    label="Фамилия"
                    name="lastname"
                    register={register}
                    errors={errors as Record<string, FieldError | undefined>}
                />
                <ContactInputForm
                    isTelegram={isTelegram}
                    setIsTelegram={setIsTelegram}
                    contactValue={contactValue}
                    setContactValue={setContactValue}
                    register={register}
                    errors={errors as Record<string, FieldError | undefined>}
                />
            </div>

            <div className={`${styles.textarea} block`}>
                <TextareaForm
                    label="Опишите свою проблему"
                    name="message"
                    register={register}
                    errors={errors as Record<string, FieldError | undefined>}
                />
                <ButtonMaster type="submit">Записаться</ButtonMaster>
            </div>
        </form>
    );
}
