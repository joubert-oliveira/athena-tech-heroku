package com.athena.tech.api.domain.repositories;

import com.athena.tech.api.domain.entity.user.AthenaUserObj;
import com.athena.tech.api.resources.orm.AthenaUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Repository
@Transactional
public interface AthenaUserRepository
        extends JpaRepository<AthenaUser, Integer> {

    @Query("select new com.athena.tech.api.domain.entity.user.AthenaUserObj(us.idUser, us.fullName, us.nickname, us.email, us.password, us.accountType, us.isActive) from AthenaUser us")
    List<AthenaUserObj> getAllAthenaUsers();

    @Query("select new com.athena.tech.api.domain.entity.user.AthenaUserObj(us.idUser, us.fullName, us.nickname, us.email, us.password, us.accountType, us.isActive) from AthenaUser us where us.idUser = ?1")
    AthenaUserObj findUserById(Integer id);

    @Modifying
    @Query(value = "insert into athena_user (full_name, nickname, email, password, account_type, is_active, birth_date) values (?1, ?2, ?3, ?4, ?5, ?6, ?7)", nativeQuery = true)
    void createNewAthenaUser(String fullName, String nickname, String email, String password, String accountType, Integer isActive, LocalDate birth_date);

    @Modifying
    @Query("update AthenaUser us set us.password = ?2 where idUser=?1")
    void updatePassword(Integer id, String content);

    @Modifying
    @Query("update AthenaUser us set us.fullName = ?2 where idUser=?1")
    void updateFullName(Integer id, String content);

    @Modifying
    @Query("update AthenaUser us set us.nickname = ?2 where idUser=?1")
    void updateNickname(Integer id, String content);

    @Modifying
    @Query("update AthenaUser us set us.email = ?2 where idUser=?1")
    void updateEmail(Integer id, String content);

    @Modifying
    @Query("update AthenaUser us set us.isActive = 1 where idUser=?1")
    void reactiveAthenaUser(Integer id);

    @Modifying
    @Query("update AthenaUser us set us.isActive = 0 where idUser=?1")
    void updateUserToInative(Integer id);

    AthenaUser findByNickname(String username);

    AthenaUser findByNicknameAndPassword(String nickname, String password);

    @Query("select a from AthenaUser a where a.idUser = ?1")
    AthenaUser findAthenaUserByIdUser(Integer idUser);

}
