package am.itspace.townrestaurantscommon.mapper;

import am.itspace.townrestaurantscommon.dto.user.CreateUserDto;
import am.itspace.townrestaurantscommon.dto.user.UserOverview;
import am.itspace.townrestaurantscommon.entity.User;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    comments = "version: 1.5.3.Final, compiler: javac, environment: Java 17.0.4.1 (Amazon.com Inc.)"
)
@Component
public class UserMapper2Impl implements UserMapper2 {

    @Override
    public User mapToEntity(CreateUserDto createUserDto) {
        if ( createUserDto == null ) {
            return null;
        }

        User.UserBuilder user = User.builder();

        user.firstName( createUserDto.getFirstName() );
        user.lastName( createUserDto.getLastName() );
        user.email( createUserDto.getEmail() );
        user.password( createUserDto.getPassword() );
        user.enabled( createUserDto.isEnabled() );

        return user.build();
    }

    @Override
    public UserOverview mapToResponseDto(User user) {
        if ( user == null ) {
            return null;
        }

        UserOverview.UserOverviewBuilder userOverview = UserOverview.builder();

        userOverview.id( user.getId() );
        userOverview.firstName( user.getFirstName() );
        userOverview.lastName( user.getLastName() );
        userOverview.email( user.getEmail() );
        userOverview.role( user.getRole() );

        return userOverview.build();
    }

    @Override
    public List<UserOverview> mapToResponseDtoList(List<User> users) {
        if ( users == null ) {
            return null;
        }

        List<UserOverview> list = new ArrayList<UserOverview>( users.size() );
        for ( User user : users ) {
            list.add( mapToResponseDto( user ) );
        }

        return list;
    }
}
