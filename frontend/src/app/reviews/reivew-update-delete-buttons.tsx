// "use client";
//
// import styles from "@/app/reviews/reviews.module.css";
// import { deleteReview } from "@/app/reviews/reviewService";
//
// // const ReviewComponent = ({ review, onEdit, onDelete }) => (
// //     <div className={styles.buttonContainer}>
// //         <button className={styles.editButton} onClick={() => onEdit(review)}>
// //             ✏️ 수정
// //         </button>
// //         <button className={styles.deleteButton} onClick={() => onDelete(review.id)}>
// //             ❌ 삭제
// //         </button>
// //     </div>
// // );
// //
// // export default ReviewComponent;
//
// export default function ReviewUpdateDeleteButtons({
//   review,
//   reviews,
//   setReviews,
//   setEditingReview,
//   token,
// }: any) {
//   const handleDelete = async (reviewId: number) => {
//     await deleteReview(reviewId, token);
//     setReviews(reviews.filter((review) => review.id !== reviewId));
//   };
//
//   return (
//     <div className={styles.buttonContainer}>
//       <button
//         className={styles.editButton}
//         onClick={() => setEditingReview(review)}
//       >
//         ✏️ 수정
//       </button>
//       <button
//         className={styles.deleteButton}
//         onClick={() => handleDelete(review.id)}
//       >
//         ❌ 삭제
//       </button>
//     </div>
//   );
// }
