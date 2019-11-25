package com.watertank.test.app;

import java.util.HashSet;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;

import com.watertank.test.entity.Credentials;
import com.watertank.test.entity.User;

/**
 * A utility class implements methods for validation services.
 * 
 * @author The Hoff
 */
@Component
@PropertySource("classpath:validation.properties")
public class UserValidator {

	@Value("${NotEmpty}")
	private String notEmpty;
	@Value("${Size.userForm.username}")
	private String usernameLength;
	@Value("${Duplicate.userForm.username}")
	private String usernameDuplicate;
	@Value("${Duplicate.userForm.email}")
	private String emailDuplicate;
	@Value("${Size.userForm.password}")
	private String passwordLength;
	@Value("${Diff.userForm.confirmPassword}")
	private String passwordNotMatch;
	@Value("${User.userForm.exist}")
	private String badCradentials;

	@Autowired
	private com.watertank.test.dao.UserService userStub;



	private static final Logger log = LoggerFactory.getLogger(UserValidator.class);


	public 	Set<String> validateLoginFrom(Object o, Errors errors) {
		Credentials credentials = (Credentials) o;
		Set<String> appErrors = new HashSet<>();
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "email", "NotEmpty");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "NotEmpty");
		if (userStub.findByUserPasswordAndEmail(credentials.getPassword(),credentials.getEmail()) == null) {
			errors.rejectValue("email", "Size.userForm.username");
			errors.rejectValue("password", "Size.userForm.password");
			appErrors.add(badCradentials);
		}
		return appErrors;

	}

	public 	Set<String> validateRegistrationForm(Object o, Errors errors) {
		User user = (User) o;
		Set<String> appErrors = new HashSet<>();
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "username", "NotEmpty");
		if (user.getUsername().length() < 6 || user.getUsername().length() > 30) {
			errors.rejectValue("username", "Size.userForm.username");
			appErrors.add(usernameLength);
		}
		if (userStub.findByUsername(user.getUsername()) != null) {
			errors.rejectValue("username", "Duplicate.userForm.username");
			appErrors.add(usernameDuplicate);
		}

		if (userStub.findByEmail(user.getEmail()) != null) {
			errors.rejectValue("email", "Duplicate.userForm.email");
			appErrors.add(emailDuplicate);
		}

		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "NotEmpty");
		if (user.getPassword().length() < 6 || user.getPassword().length() > 30) {
			errors.rejectValue("password", "Size.userForm.password");
			appErrors.add(passwordLength);
		}

		if (!user.getConfirmPassword().equals(user.getPassword())) {
			errors.rejectValue("confirmPassword", "Diff.userForm.confirmPassword");
			appErrors.add(passwordNotMatch);
		}

		return appErrors;
	}

}
