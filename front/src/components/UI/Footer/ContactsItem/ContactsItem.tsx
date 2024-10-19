import styles from "./ContactsItem.module.scss";

interface ContactsItemProps {
    children: string;
    image: string;
}

export default function ContactsItem(props : ContactsItemProps) {

    return (
        <div className={styles.social}>
            <img src={`/${props.image}.svg`} alt=""/>
            <span>{props.children}</span>
        </div>
    );
}
