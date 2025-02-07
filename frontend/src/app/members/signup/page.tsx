"use client";

import { useState } from "react";
import { 회원가입처리 } from "./actions";
import styles from "./page.module.css";

export default function SignupPage() {
    const [formData, setFormData] = useState({ username: "", password: "", nickname: "" });

    const handleChange = (e: React.ChangeEvent<HTMLInputElement>) => {
        setFormData({ ...formData, [e.target.name]: e.target.value });
    };

    // 아이디와 비밀번호가 모두 입력되어야 버튼이 활성화됨
    const isEnabled =
        formData.username.trim() !== "" && formData.password.trim() !== "";

    return (
        <div className={styles.container}>
            <div className={styles.formWrapper}>
                <h1 className={styles.title}>회원가입</h1>
                <form action={회원가입처리} className={styles.form}>
                    <input
                        name="username"
                        type="text"
                        placeholder="아이디"
                        value={formData.username}
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
                    <input
                        name="nickname"
                        type="text"
                        placeholder="닉네임 (선택)"
                        value={formData.nickname}
                        onChange={handleChange}
                        className={styles.input}
                    />
                    <button
                        type="submit"
                        className={isEnabled ? styles.button : styles.disabledButton}
                        disabled={!isEnabled}
                    >
                        회원가입
                    </button>
                </form>
            </div>
        </div>
    );
}
