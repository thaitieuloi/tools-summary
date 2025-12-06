package com.ttl.tool.core.service;

import com.ttl.tool.core.exception.NotFoundException;
import com.ttl.tool.domain.entity.User;
import com.ttl.tool.domain.repository.UserRepository;
import com.ttl.tool.shared.dto.UserInput;
import com.ttl.tool.shared.dto.UserSearchInput;
import jakarta.persistence.criteria.Predicate;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    @Transactional(readOnly = true)
    public User getUser(UUID id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("User not found with id: " + id));
    }

    @Transactional(readOnly = true)
    public Page<User> searchUsers(UserSearchInput filter, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Specification<User> spec = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (filter != null) {
                if (StringUtils.hasText(filter.getUsername())) {
                    predicates.add(
                            cb.like(cb.lower(root.get("username")), "%" + filter.getUsername().toLowerCase() + "%"));
                }
                if (StringUtils.hasText(filter.getEmail())) {
                    predicates.add(cb.like(cb.lower(root.get("email")), "%" + filter.getEmail().toLowerCase() + "%"));
                }
            }
            return cb.and(predicates.toArray(new Predicate[0]));
        };
        return userRepository.findAll(spec, pageable);
    }

    @Transactional
    public User userCreate(UserInput input) {
        if (input == null || !StringUtils.hasText(input.getUsername()) || !StringUtils.hasText(input.getEmail())) {
            throw new IllegalArgumentException("Username and Email are required");
        }

        User user = User.builder()
                .username(input.getUsername())
                .email(input.getEmail())
                .active(true)
                .build();
        return userRepository.save(user);
    }

    @Transactional
    public User userUpdate(UUID id, UserInput input) {
        User user = getUser(id);
        if (input != null) {
            if (StringUtils.hasText(input.getUsername())) {
                user.setUsername(input.getUsername());
            }
            if (StringUtils.hasText(input.getEmail())) {
                user.setEmail(input.getEmail());
            }
        }
        return userRepository.save(user);
    }

    @Transactional
    public boolean userDelete(UUID id) {
        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
