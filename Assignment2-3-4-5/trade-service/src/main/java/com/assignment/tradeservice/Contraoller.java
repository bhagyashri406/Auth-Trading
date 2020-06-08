package com.assignment.tradeservice;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

@RestController
public class Contraoller {

	@Autowired
	TickerRepository tickerRepository;

	@Autowired
	BalanceRepository balanceRepository;

	@Autowired
	private AuthServiceProxy proxy;

	@GetMapping("/home")
	public ModelAndView home() {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("home.html");
		return modelAndView;
	}

	@GetMapping("/register")
	public ModelAndView register() {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("register.html");
		return modelAndView;
	}

	@GetMapping("/login")
	public ModelAndView loginpage() {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("error", "");
		modelAndView.setViewName("login.html");
		return modelAndView;
	}

//@RequestMapping(value = "/save", method = RequestMethod.POST)
//public ModelAndView save(@ModelAttribute User user) {
//	
//	ResponseEntity<User> responseEntity = new RestTemplate().postForEntity("http://localhost:8081/save",user, User.class) ;
//	ModelAndView modelAndView = new ModelAndView();
//	if(responseEntity!=null) {
//	modelAndView.addObject("user", user);
//	}
//	modelAndView.setViewName("login.html");
//	return modelAndView;
//}

	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public ModelAndView save(@ModelAttribute User user) {

		User response = proxy.saveUser(user);
		ModelAndView modelAndView = new ModelAndView();
		if (response != null) {
			modelAndView.addObject("user", user);
		}
		modelAndView.setViewName("login.html");
		return modelAndView;
	}

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public ModelAndView login(@ModelAttribute User user) {

		Map<String, Integer> uriVariable = new HashMap();
		uriVariable.put("id", user.getId());

		User responseEntity =proxy.GetUserById(user.getId());

		if (responseEntity != null && responseEntity.getPassword()!=null
				&& responseEntity.getPassword().equals(user.getPassword())) {

			if (!balanceRepository.findById(user.getId()).isPresent()) {
				Balance balance = new Balance();
				balance.setQuantity(0);
				balance.setBalance(100000);
				balance.setId(user.getId());
				balanceRepository.save(balance);
			}

			ModelAndView modelAndView = new ModelAndView();
			modelAndView.addObject("user", user);

			Iterable<Ticker> iterable = tickerRepository.findAll();
			modelAndView.addObject("entries", iterable);
			modelAndView.setViewName("trading.html");

			return modelAndView;
		} else {
			ModelAndView modelAndView = new ModelAndView();
			modelAndView.addObject("error", "UserId or Password is not correct");
			modelAndView.setViewName("login.html");
			return modelAndView;
		}

	}

	@RequestMapping(value = "/trading", method = RequestMethod.POST)
	public ModelAndView trading(@ModelAttribute Ticker ticker, @ModelAttribute Balance balance) {

		List<Ticker> iterable = tickerRepository.findAll();
		HashMap<String, Double> Tickermap = new HashMap<>();
		for (Ticker ticker2 : iterable) {

			Tickermap.put(ticker2.getTicker(), ticker2.getPrice());

		}
		System.out.println(ticker.getTicker().toUpperCase() + " contains"
				+ Tickermap.containsKey(ticker.getTicker().toUpperCase()));

		if (Tickermap.containsKey(ticker.getTicker().toUpperCase())) {
			double d = Tickermap.get(ticker.getTicker());

			double amount = balance.getQuantity() * d;

			Optional<Balance> balanceRemaining = balanceRepository.findById(balance.getId());
			System.out.println("---------- Ticker" + ticker.getTicker() + " UserId :" + balance.getId() + "quality :"
					+ balance.getQuantity());

			double remaningBal = balanceRemaining.get().getBalance();

			if (remaningBal - amount >= 0) {

				balanceRemaining.get().setBalance(remaningBal - amount);
				balanceRepository.save(balanceRemaining.get());
				ModelAndView modelAndView = new ModelAndView();
				modelAndView.addObject("balance", remaningBal - amount);

				User user = new User();
				user.setId(balance.getId());
				Iterable<Ticker> iterable1 = tickerRepository.findAll();
				modelAndView.addObject("entries", iterable1);
				modelAndView.addObject("user", user);
				modelAndView.addObject("error", "");

				modelAndView.setViewName("trad-success.html");

				return modelAndView;

			} else {

				ModelAndView modelAndView = new ModelAndView();
				modelAndView.addObject("error", "Your Balance is low to buy this stoke ");

				User user = new User();
				user.setId(balance.getId());
				Iterable<Ticker> iterable1 = tickerRepository.findAll();
				modelAndView.addObject("entries", iterable1);
				modelAndView.addObject("user", user);

				modelAndView.setViewName("trading.html");
				return modelAndView;

			}

		} else {
			ModelAndView modelAndView = new ModelAndView();
			modelAndView.addObject("error", "Ticker value is not match from above value");

			User user = new User();
			user.setId(balance.getId());
			Iterable<Ticker> iterable1 = tickerRepository.findAll();
			modelAndView.addObject("entries", iterable1);
			modelAndView.addObject("user", user);

			modelAndView.setViewName("trading.html");
			return modelAndView;
		}

	}

}
