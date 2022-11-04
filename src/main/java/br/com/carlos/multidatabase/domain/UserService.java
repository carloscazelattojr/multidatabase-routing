package br.com.carlos.multidatabase.domain;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository repository;

    @Transactional(readOnly = true)
    public UserEntity getUser(Long id){

        log.info("[getUser]");
        return repository.findById(id).get();
    }

    @Transactional
    public UserEntity getUserWrite(Long id){
        log.info("[getUser]");
        return repository.findById(id).get();
    }


    @Transactional
    public UserEntity insertUser(UserEntity userEntity){
        log.info("[insertUser]");
        return repository.save(userEntity);

    }
}