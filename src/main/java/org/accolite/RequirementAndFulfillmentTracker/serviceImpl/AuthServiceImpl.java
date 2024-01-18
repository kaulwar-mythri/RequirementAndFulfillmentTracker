package org.accolite.RequirementAndFulfillmentTracker.serviceImpl;

import org.accolite.RequirementAndFulfillmentTracker.config.JWTService;
import org.accolite.RequirementAndFulfillmentTracker.entity.Account;
import org.accolite.RequirementAndFulfillmentTracker.entity.GoogleTokenPayload;
import org.accolite.RequirementAndFulfillmentTracker.entity.Role;
import org.accolite.RequirementAndFulfillmentTracker.entity.UserRole;
import org.accolite.RequirementAndFulfillmentTracker.model.AccountDTO;
import org.accolite.RequirementAndFulfillmentTracker.model.UserRoleDTO;
import org.accolite.RequirementAndFulfillmentTracker.repository.UserRoleRepository;
import org.accolite.RequirementAndFulfillmentTracker.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class AuthServiceImpl implements AuthService {
    @Autowired
    JWTService jwtService;
    @Autowired
    UserRoleRepository userRoleRepository;
    private String validationEndpoint = "https://www.googleapis.com/oauth2/v3/tokeninfo";
    private boolean isNewUser = false;
    @Override
    public ResponseEntity<Map<String, Object>> createUserRole(String googleAuthToken) {
        try {
            String tokenPayload = validateGoogleToken(googleAuthToken);

            if(tokenPayload != null) {
                Map<String, Object> responseMap = new HashMap<>();
                responseMap.put("status", "success");
                responseMap.put("authToken", tokenPayload);
                responseMap.put("isNewuser", isNewUser);

                return ResponseEntity.ok(responseMap);
            } else {
                Map<String, Object> errorMap = new HashMap<>();
                errorMap.put("status", "error");
                errorMap.put("message", "Invalid Google Token");

                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorMap);
            }
        } catch(Exception e) {
            Map<String, Object> errorMap = new HashMap<>();
            errorMap.put("status", "error");
            errorMap.put("message", "Error occured");

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorMap);
        }
    }

    private String validateGoogleToken(String googleAuthToken) {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<GoogleTokenPayload> response = restTemplate.getForEntity(validationEndpoint + "?id_token=" + googleAuthToken, GoogleTokenPayload.class);

        String jwtToken;
        if(response.getBody() != null) {
            String email = response.getBody().getEmail();   //assuming I get email back
            UserRole userRole = userRoleRepository.findByEmailId(email)
                    .orElse(null);


            if(userRole == null) {
                var newUserRole = UserRole.builder()
                        .emailId(response.getBody().getEmail())
                        .role(Role.DEFAULT)
                        .accounts(new HashSet<>())
                        .build();

                jwtToken = jwtService.generateJWTToken(userRoleRepository.save(newUserRole));
                isNewUser = true;
            } else {
                jwtToken = jwtService.generateJWTToken(userRole);
            }
        } else {
            return null;
        }

        if(response.getStatusCode() == HttpStatus.OK)
            return jwtToken;
        else
            return null;
    }

    @Override
    public ResponseEntity<UserRoleDTO> getUser(String googleAuthToken) {
        String email = jwtService.extractUsernameFromJWTToken(googleAuthToken);
        UserRole user = userRoleRepository.findByEmailId(email).orElse(null);
        System.out.println(user);
        if(user != null) {
            Set<Account> accounts = user.getAccounts();
            Set<AccountDTO> accountDTOS = convertAccountsToDTOs(accounts);
            UserRoleDTO userRoleDTO = UserRoleDTO.builder()
                    .id(user.getId())
                    .emailId(user.getEmailId())
                    .employeeId(user.getEmployeeId())
                    .accounts(accountDTOS)
                    .role(user.getRole())
                                    .build();
            return ResponseEntity.ok(userRoleDTO);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }
    }

    public Set<AccountDTO> convertAccountsToDTOs(Set<Account> accounts) {
        return accounts.stream().map(account -> {
            AccountDTO accountDTO = AccountDTO.builder()
                    .account_id(account.getAccount_id())
                    .name(account.getName())
                    .hierarchyTag(account.getHierarchyTag())
                    .parentId(account.getParentId())
                    .build();
            return accountDTO;
        }).collect(Collectors.toSet());
    }
}
