"use client";

import { useState } from "react";
import { 로그인처리 } from "./actions";
import styles from "./page.module.css";

export default function Page() {
    const [formData, setFormData] = useState({ text: "", password: "" });

    const handleChange = (e: React.ChangeEvent<HTMLInputElement>) => {
        setFormData({ ...formData, [e.target.name]: e.target.value });
    };

    const isEnabled = formData.text.trim() !== "" && formData.password.trim() !== "";

    return (
        <div className={styles.container}>
            <div className={styles.formWrapper}>
                <h1 className={styles.title}>로그인</h1>
                <form action={로그인처리} className={styles.form}>
                    <input
                        name="text"
                        type="text"
                        placeholder="계정이름"
                        value={formData.text}
                        onChange={handleChange}
                        className={styles.input}
                    />
                    <input
                        name="password"
                        type="password"
                        placeholder="비밀번호"
                        value={formData.password}
                        onChange={handleChange}
                        className={styles.input}
                    />
                    <button
                        type="submit"
                        className={isEnabled ? styles.button : styles.disabledButton}
                        disabled={!isEnabled}
                    >
                        Log in
                    </button>
                </form>
                <div className={styles.signupContainer}>
                    <a href="/members/signup" className={styles.signupLink}>
                        회원가입
                    </a>
                </div>
            </div>
        </div>
    );
}
