"use client";

import { useState } from "react";
import styles from "./profile.module.css";

interface DeleteAccountInputProps {
    username: string;
}

export default function DeleteAccountInput({ username }: DeleteAccountInputProps) {
    const [deleteInput, setDeleteInput] = useState("");
    const [isEditingDelete, setIsEditingDelete] = useState(false);

    const handleDelete = () => {
        if (deleteInput === username) {
            console.log("계정 삭제:", username);
        }
    };

    return (
        <div className={styles.inputGroup}>
            <div className={styles.inputWrapper}>
                <input
                    type="text"
                    value={deleteInput}
                    onChange={(e) => setDeleteInput(e.target.value)}
                    onFocus={() => setIsEditingDelete(true)}
                    placeholder="아이디 입력"
                    className={`${styles.input} ${isEditingDelete ? styles.activeInput : ""}`}
                />
                {isEditingDelete && deleteInput !== "" && (
                    <button className={styles.resetButton} onClick={() => setDeleteInput("")}>
                        ↺
                    </button>
                )}
            </div>

            {/* 버튼과 입력 칸 사이 여백 추가 */}
            <button
                onClick={handleDelete}
                className={`${styles.deleteButton} ${
                    deleteInput === username ? styles.activeDeleteButton : ""
                }`}
                disabled={deleteInput !== username}
            >
                계정 삭제
            </button>
        </div>
    );
}
