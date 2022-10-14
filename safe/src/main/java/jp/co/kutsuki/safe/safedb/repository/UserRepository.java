package jp.co.kutsuki.safe.safedb.repository;

import org.springframework.data.repository.CrudRepository;

import jp.co.kutsuki.safe.entity.User;

public interface UserRepository extends CrudRepository<User, Integer>{

}
