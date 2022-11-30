package br.fai.models.client.service.impl;

import br.fai.models.client.service.RestService;
import br.fai.models.entities.UserModel;
import br.fai.models.enums.UserType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpSession;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    public static final String FIND_BY_ID_ENDPOINT = "user/";
    @Mock
    private HttpSession httpSession;

    @Mock
    private RestService<UserModel> restService;

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private UserServiceImpl cut;
    // sut - system under test
    // cut - class under test

    private UserModel createFirstMockUser() {
        UserModel user = new UserModel();
        user.setId(1);
        user.setUsername("tiburssinho");
        user.setFullName("Tiburssio Tiburssius");
        user.setEmail("tiburssinho@gmail.com");
        user.setPassword("123");
        user.setType(UserType.ADMINISTRADOR);
        user.setActive(true);

        Timestamp dateTime = new Timestamp(System.currentTimeMillis());

        user.setLastModified(dateTime);
        user.setCreatedAt(dateTime);

        return user;

    }

    private UserModel createSecondMockUser() {
        UserModel user = new UserModel();
        user.setId(2);
        user.setUsername("aroldo");
        user.setFullName("Aroldo Aroldus");
        user.setEmail("aroldo@gmail.com");
        user.setPassword("123");
        user.setType(UserType.USUARIO);
        user.setActive(false);

        Timestamp dateTime = new Timestamp(System.currentTimeMillis());

        user.setLastModified(dateTime);
        user.setCreatedAt(dateTime);

        return user;
    }

    @Test
    void ShouldInjectBeans() {
        assertNotNull(cut);
        assertNotNull(httpSession);
        assertNotNull(restService);
        assertNotNull(restTemplate);
    }

    @Test
    void find_whenValidUserIsProvided_shouldCreate() {

        UserModel user = createFirstMockUser();

        final int id = cut.create(user);

        assertThat(id).isEqualTo(-1);

    }

    @Test
    void find_whenNoUsersFound_shouldReturnEmptyList() {
        List<UserModel> users = cut.find();

        assertThat(users).isEmpty();
    }

    @Test
    void find_whenUsersAreFound_shouldReturnUsers() {

        final UserModel firstMockUser = createFirstMockUser();

        final UserModel secondMockUser = createSecondMockUser();

        List<UserModel> usersMockList = Arrays.asList(firstMockUser, secondMockUser);

        when(restService.get(FIND_BY_ID_ENDPOINT, null)).thenReturn(usersMockList);

        List<UserModel> users = cut.find();

        assertThat(users).isNotNull();
        assertThat(users).isNotEmpty();
        assertThat(users).hasSize(2);


        UserModel firstUser = users.get(0);
        UserModel secondUser = users.get(1);

        assertThat(firstUser.getUsername()).isEqualTo("tiburssinho");

    }

    @Test
    void findById_whenInvalidIdIsProvided_shouldReturnNull() {
        UserModel user = cut.findById(-1);

        assertThat(user).isNull();
    }

    @Test
    void findById_whenUserIsNotFound_shouldReturnNull() {

        UserModel user = cut.findById(5);

        assertThat(user).isNull();

    }

    @Test
    void findById_whenUserExists_shouldReturnNull() {

        final int validId = 1;

        UserModel userMock = createFirstMockUser();

        String endpoint = FIND_BY_ID_ENDPOINT + validId;

        when(restService.getById(endpoint, UserModel.class, null)).thenReturn(userMock);

        UserModel user = cut.findById(validId);

        assertThat(user).isNotNull();

        verify(restService).getById(endpoint, UserModel.class, null);

    }

    @Test
    void update_whenInvalidIdIsProvided_shouldReturnFalse() {

        boolean response = cut.update(-1, null);

        assertThat(response).isFalse();

    }

    @Test
    void update_whenNullEntityProvided_shouldReturnFalse() {

        boolean response = cut.update(1, null);

        assertThat(response).isFalse();

    }

    @Test
    void update_whenEntityIdDoNotMatch_shouldReturnFalse() {

        UserModel firstMockUser = createFirstMockUser();

        firstMockUser.setId(10);

        boolean response = cut.update(1, firstMockUser);

        assertThat(response).isFalse();

    }

    @Test
    void update_whenValidDataIsProvided_shouldReturnTrue() {

        UserModel firstMockUser = createFirstMockUser();

        final String endpoint = FIND_BY_ID_ENDPOINT + firstMockUser.getId();

        when(restService.put(endpoint, firstMockUser, null)).thenReturn(true);

        boolean response = cut.update(firstMockUser.getId(), firstMockUser);

        assertThat(response).isTrue();

        verify(restService).put(endpoint, firstMockUser, null);

    }

}