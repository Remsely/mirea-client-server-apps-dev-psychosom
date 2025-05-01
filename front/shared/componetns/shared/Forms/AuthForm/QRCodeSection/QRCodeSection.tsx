import styles from "./QRCodeSection.module.scss";
import {QRCodeSVG} from "qrcode.react";

export function QRCodeSection(props: { qrLink: string }) {
    return (
        <div className={styles.block}>
            <h2 className="">Подтвердите аккаунт при помощи Telegram.
                Бот будет помогать вам следить за консультациями.</h2>
            <div>
                <QRCodeSVG id="qrCode" className="qrCode" value={props.qrLink} size={350} fgColor={"#6E522D"} imageSettings={{
                    src: "/logo-without-bg.svg",
                    height: 50,
                    width: 50,
                    opacity: 1,
                    excavate: true,
                }}/>
            </div>
            <p>
                Сканируйте QR-код или перейдите по <a className={styles.link} href={props.qrLink} target="_blank"
                                       rel="noopener noreferrer">ссылке</a> и нажмите кнопку <b>/start</b>
            </p>
        </div>
    );
}
