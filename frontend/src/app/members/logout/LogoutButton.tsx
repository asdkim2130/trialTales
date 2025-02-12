"use client";

import { logout } from "./actions";
import styles from "./logout.module.css";

export default function LogoutButton() {
    return (
        <button className={styles.logoutButton} onClick={() => logout()}>
            로그아웃
        </button>
    );
}
