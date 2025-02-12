"use client";

import { useState } from "react";
import styles from "./profile.module.css";
import {handleAccountDeletion} from "@/app/members/client-api";

interface DeleteAccountInputProps {
    username: string;
}

export default function DeleteAccountInput({ username }: DeleteAccountInputProps) {
    const [deleteInput, setDeleteInput] = useState("");

    const handleDelete = async () => {
        if (deleteInput === username) {
            await handleAccountDeletion(username);
        }
    };

    return (
        <div className={styles.inputGroup}>
            <input
                type="text"
                value={deleteInput}
                onChange={(e) => setDeleteInput(e.target.value)}
                placeholder="아이디 입력"
                className={styles.input}
            />
            <button onClick={handleDelete} className={styles.deleteButton} disabled={deleteInput !== username}>
                계정 삭제
            </button>
        </div>
    );
}
