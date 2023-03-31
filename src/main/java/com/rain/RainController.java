package com.rain;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.rain.beans.AuthBean;
import com.rain.beans.AuthorizationBean;
import com.rain.beans.GroupMappingBean;
import com.rain.beans.GroupReqBean;
import com.rain.beans.RegisterAppBean;
import com.rain.beans.RegisterUserBean;
import com.rain.beans.SubscriptionRequestBean;
import com.rain.process.auth.AuthProcessor;
import com.rain.process.group.GroupProcessor;
import com.rain.process.register.ApplicationRegisterProcessor;
import com.rain.process.subscription.SubscriptionProcessor;
import com.rain.process.user.UserProcessor;
import com.rain.utils.InvokeAllDefaultDBOperations;
import com.rain.utils.RainException;
import com.rain.utils.RequestValidator;
import com.rain.utils.Response;
/**
 * 
 * @author Bikramaditya Gochhayat
 *
 */
@RestController
@ComponentScan
@RequestMapping("/rain")
public class RainController {

	@Autowired
	private AuthorizationBean bean;

	@Autowired
	private Response response;

	@Autowired
	private SubscriptionProcessor subProcessor;

	@Autowired
	private ApplicationRegisterProcessor appProcessor;

	@Autowired
	private UserProcessor userProcessor;

	@Autowired
	private AuthProcessor authProcessor;
	
	@Autowired
	private GroupProcessor groupProcessor;

	private static Logger log = LoggerFactory.getLogger(RainController.class);

	

	@RequestMapping(value = "/subscribe", method = RequestMethod.POST)
	public Object subscribe(
			@RequestBody SubscriptionRequestBean subscriptionRequestBean) {

		log.debug("Entered the subscribe rest method");
		try {
			RequestValidator.validateSubscription(subscriptionRequestBean);
		} catch (RainException e) {

			response.setCode(e.getCode());
			response.setMessage(e.getMessage());
			response.setStatus(e.getStatus());
			response.setValue(null);

			return response;

		}

		try {
			subProcessor.processSubscription(subscriptionRequestBean);
		} catch (RainException e) {

			response.setCode(e.getCode());
			response.setMessage(e.getMessage());
			response.setValue(e.getValue());
			response.setStatus(e.getStatus());

			return response;
		}
		return null;

	}

	@RequestMapping(value = "/registerApp", method = RequestMethod.POST)
	public Object registerApplication(@RequestBody RegisterAppBean appBean,
			@RequestHeader("payId") String payId) {

		log.debug("Entered the registerApplication rest method");
		try {
			RequestValidator.validateRegisterApp(appBean, payId);
		} catch (RainException e) {
			response.setCode(e.getCode());
			response.setMessage(e.getMessage());
			response.setStatus(e.getStatus());
			response.setValue(null);

			return response;
		}

		try {
			appProcessor.processAppRegistration(appBean, payId);
		} catch (RainException e) {
			response.setCode(e.getCode());
			response.setMessage(e.getMessage());
			response.setValue(e.getValue());
			response.setStatus(e.getStatus());

			return response;
		}
		return null;
	}

	@RequestMapping(value = "/registerUser", method = RequestMethod.POST)
	public Object registerUser(@RequestBody RegisterUserBean userBean,
			@RequestHeader("appId") String appId) {
		log.debug("Entered the registerUser rest method");
		try {
			RequestValidator.validateRegisterUser(userBean, appId);
		} catch (RainException e) {
			response.setCode(e.getCode());
			response.setMessage(e.getMessage());
			response.setStatus(e.getStatus());
			response.setValue(null);

			return response;
		}
		try {
			userProcessor.processUserRegistration(userBean, appId);
		} catch (RainException e) {
			response.setCode(e.getCode());
			response.setMessage(e.getMessage());
			response.setValue(e.getValue());
			response.setStatus(e.getStatus());

			return response;
		}
		return null;
	}

	@RequestMapping(value = "/authenticate", method = RequestMethod.POST)
	public Object authenticateByUsernameAndPassword(
			@RequestBody AuthBean authBean, @RequestHeader("appId") String appId) {
		log.debug("Entered authenticateByUsernameAndPassword method");
		try {
			RequestValidator.validateAuth(authBean, appId);
		} catch (RainException e) {
			response.setCode(e.getCode());
			response.setMessage(e.getMessage());
			response.setStatus(e.getStatus());
			response.setValue(null);

			return response;
		}
		try {
			authProcessor.processAuth(authBean, appId);
		} catch (RainException e) {
			response.setCode(e.getCode());
			response.setMessage(e.getMessage());
			response.setValue(e.getValue());
			response.setStatus(e.getStatus());

			return response;
		}
		return null;
	}
	
	@RequestMapping(value = "/createGroup", method = RequestMethod.POST)
	public Object createGroup(@RequestBody GroupReqBean grpReqBean, @RequestHeader("appId") String appId, @RequestHeader("sso") String sso)
	{
		log.debug("Entered Group Creation method");
		try {
			RequestValidator.validateGroupRequest(grpReqBean, appId, sso);
		} catch (RainException e) {
			response.setCode(e.getCode());
			response.setMessage(e.getMessage());
			response.setStatus(e.getStatus());
			response.setValue(null);

			return response;
		}
		try {
		groupProcessor.processCreateGroup(grpReqBean, appId, sso);
		} catch (RainException e) {
			response.setCode(e.getCode());
			response.setMessage(e.getMessage());
			response.setValue(e.getValue());
			response.setStatus(e.getStatus());

			return response;
		}
		return null;
	}
	

	@RequestMapping(value = "/getAllGroupsAndSubGroups", method = RequestMethod.GET)
	public Object getAllGroupsAndSubGroups(@RequestHeader("appId") String appId, @RequestHeader("sso") String sso)
	{
		
		try {
		groupProcessor.getAllGroupsAndSubGroups(appId, sso);
		} catch (RainException e) {
			response.setCode(e.getCode());
			response.setMessage(e.getMessage());
			response.setValue(e.getValue());
			response.setStatus(e.getStatus());

			return response;
		}
		return null;
	}
	
	@RequestMapping(value = "/mapUserToGroup", method = RequestMethod.POST)
	public Object mapUserToGroup(@RequestBody GroupMappingBean grpMapBean, @RequestHeader("appId") String appId, @RequestHeader("sso") String sso)
	{
		log.debug("Entered Group Mapping method");
		try {
			RequestValidator.validateGroupMappingRequest(grpMapBean, appId, sso);
		} catch (RainException e) {
			response.setCode(e.getCode());
			response.setMessage(e.getMessage());
			response.setStatus(e.getStatus());
			response.setValue(null);

			return response;
		}
		
		try {
			groupProcessor. mapUser(grpMapBean, appId, sso);
			} catch (RainException e) {
				response.setCode(e.getCode());
				response.setMessage(e.getMessage());
				response.setValue(e.getValue());
				response.setStatus(e.getStatus());

				return response;
			}
			return null;
	
	}
	
	
	@RequestMapping(value = "/getAllUsers", method = RequestMethod.POST)
	public Object getAllUsers(@RequestHeader("appId") String appId, @RequestHeader("sso") String sso)
	{
		log.debug("Entered Group Mapping method");
		try {
			RequestValidator.validateUserReuest(appId, sso);
		} catch (RainException e) {
			response.setCode(e.getCode());
			response.setMessage(e.getMessage());
			response.setStatus(e.getStatus());
			response.setValue(null);

			return response;
		}
		
		try {
			userProcessor.getAllUserInfo(appId, sso);
			} catch (RainException e) {
				response.setCode(e.getCode());
				response.setMessage(e.getMessage());
				response.setValue(e.getValue());
				response.setStatus(e.getStatus());

				return response;
			}
			return null;
	
	}
	


	
}
