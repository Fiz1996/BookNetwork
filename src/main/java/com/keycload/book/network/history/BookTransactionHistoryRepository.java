package com.keycload.book.network.history;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import javax.swing.text.html.Option;
import java.util.Optional;

public interface BookTransactionHistoryRepository extends JpaRepository<BookTransactionHistory,Integer> {
    @Query("""
            SELECT history 
            FROM BookTransactionHistory history
            WHERE history.user.id =  :userId
            """)
    Page<BookTransactionHistory> findAllBorrowedBooks(Pageable pageable, Integer books);

    @Query("""
            SELECT history 
            FROM BookTransactionHistory history
            WHERE history.book.owner.id = :userId
           """)
    Page<BookTransactionHistory> findAllReturnedBooks(Pageable pageable, Integer id);

    @Query("""
            SELECT 
             (COUNT (*) > 0) AS isBorrowed
             FROM BookTransactionHistory bookTransactionHistory
             WHERE bookTransactionHistory.user.id = :userId
             AND bookTransactionHistory.book.id = :bookId
             AND bookTransactionHistory.returnApproved = false
            """)
    boolean isAlreadyBorrowedByUser(Integer bookId, Integer id);


    @Query("""
            SELECT transaction 
            FROM BookTransactionHistory transaction 
            WHERE transaction.user.id = :userId
            AND transaction.book.id = :bookId
            AND transaction.returnApproved = false                                                                    
            """)
    Optional<BookTransactionHistory> findByBookIdAndUserId(Integer bookId, Integer userId);

    @Query("""
            SELECT transaction 
            FROM BookTransactionHistory transaction 
            WHERE transaction.book.id = :bookId
            AND transaction.user.id = :userId
            AND  transaction.returned = true
            AND transaction.returnApproved = false                                    
            """
    )
    Optional<BookTransactionHistory> findByBookIdAndOwner(Integer bookId, Integer id);
}
