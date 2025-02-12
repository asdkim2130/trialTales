"use client";

import {useState} from "react";
import styles from "./profile.module.css";
import {handleAccountDeletion} from "@/app/members/client-api";

interface DeleteAccountInputProps {
    username: string;
}

export default function DeleteAccountInput({ username }: DeleteAccountInputProps) {
    const [deleteInput, setDeleteInput] = useState("");
    const [isEditing, setIsEditing] = useState(false);

    const handleDelete = async () => {
        if (deleteInput === username) {
            const success = await handleAccountDeletion(username);
            if (success) {
                console.log("계정 삭제 완료!");
            }
        }
    };

    const handleReset = () => {
        setDeleteInput("");
        setIsEditing(false); // 입력 필드 비활성화
    };

    return (
        <div className={styles.inputGroup}>
            <div className={styles.inputWrapper}>
                <input
                    type="text"
                    value={deleteInput}
                    onChange={(e) => {
                        setDeleteInput(e.target.value);
                        setIsEditing(e.target.value !== "");
                    }}
                    placeholder="아이디 입력"
                    className={`${styles.input} ${isEditing ? styles.activeInput : ""}`}
                />
                {isEditing && (
                    <button className={styles.resetButton} onClick={handleReset}>
                        ↺
                    </button>
                )}
            </div>
            <button
                onClick={handleDelete}
                className={`${styles.deleteButton} ${deleteInput === username ? styles.activeDeleteButton : ""}`}
                disabled={deleteInput !== username}
            >
                계정 삭제
            </button>
        </div>
    );
}
