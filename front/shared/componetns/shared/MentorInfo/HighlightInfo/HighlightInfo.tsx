import {ReactNode} from 'react';
import styles from "./HighlightInfo.module.scss";

interface HighlightInfoProps {
    children: ReactNode;
}

export function HighlightInfo(props: HighlightInfoProps) {
    return (
        <div className={styles.highlight}>
            <p>{props.children}</p>
        </div>
    );
}
