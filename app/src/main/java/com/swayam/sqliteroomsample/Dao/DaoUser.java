package com.swayam.sqliteroomsample.Dao;

/*Created by akhil on 13/2/18.*/

/*
 * This is the dao(data access object) for the entities(table)
 * All the operations performed on a entity(table) must be mentioned here.
 */

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.swayam.sqliteroomsample.Entities.EntityAuthtenticateUser;
import com.swayam.sqliteroomsample.Entities.EntityUserPojo;

import java.util.List;

@Dao
public interface DaoUser {

    /**
     * it returns list of all available users.
     * @return(list of users)
     */
    @Query("SELECT * FROM users")
     List<EntityUserPojo> getAllUsers();

    /**
     * it returns details of user based on user_id.
     * @param userid(userid of user)
     * @return(return respective details of user based on uid)
     */
    @Query("SELECT * FROM users WHERE user_id LIKE :userid")
    EntityUserPojo getUser(String userid);


    /**
     * it is used to add the user i.e register in the sqlite database
     * @param entityUser(user object to add or register user)
     */
    @Insert
     void addUser(EntityUserPojo entityUser);


    /**
     * it checks user credentils with emailid.
     * @param email(emailid of user to check the credentials)
     * @return(returns EntityAuthtenticateUser object if user exists)
     */
    @Query("SELECT * FROM users WHERE email LIKE :email")
    EntityAuthtenticateUser checkUser(String email);

    /**
     * it is used to change the password of the user
     * @param password(new password of the user)
     * @param userid(respective userid of user to change password)
     * @return(returns 1 if udpate is successfull and 0 if it is failure)
     */
    @Query("UPDATE users SET password = :password WHERE user_id LIKE :userid")
    int changePassword(String password,String userid);

    /**
     * it is user to update userdetails
     * @param username
     * @param mobile
     * @param userid
     * @return(returns 1 if udpate is successfull and 0 if it is failure)
     */
    @Query("UPDATE users SET username = :username,mobile =:mobile WHERE user_id LIKE :userid")
    int updateUserDetails(String username,String mobile,String userid);

}
