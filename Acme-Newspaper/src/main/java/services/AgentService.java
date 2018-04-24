package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import domain.Agent;
import domain.Folder;

import repositories.AgentRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import security.UserAccountService;

@Service
@Transactional
public class AgentService {
	
	@Autowired
	private AgentRepository agentRepository;
	@Autowired
	private UserAccountService userAccountService;
	@Autowired
	private FolderService folderService;
	
	public AgentService(){
		super();
	}

	public Agent create(){
		Agent res = new Agent();
		
		UserAccount ua = this.userAccountService.create();
		res.setUserAccount(ua);
		
		res.setFolders(new ArrayList<Folder>());
		res.getFolders().add(this.folderService.create());
		res.getFolders().add(this.folderService.create());
		res.getFolders().add(this.folderService.create());
		res.getFolders().add(this.folderService.create());
		res.getFolders().add(this.folderService.create());
		
		final List<Authority> authorities = new ArrayList<Authority>();
		final Authority auth = new Authority();
		auth.setAuthority(Authority.AGENT);
		authorities.add(auth);
		res.getUserAccount().setAuthorities(authorities);
		
		return res;
	}
	
	public Agent save(final Agent agent) {
		Assert.notNull(agent);
		Assert.isTrue(!agent.getUserAccount().getUsername().isEmpty());
		Assert.isTrue(!agent.getUserAccount().getPassword().isEmpty());
		Assert.isTrue(!agent.getEmail().isEmpty());
		Assert.isTrue(!agent.getName().isEmpty());
		Assert.isTrue(!agent.getSurname().isEmpty());

		if (agent.getId() == 0) {
			final Md5PasswordEncoder encoder = new Md5PasswordEncoder();
			final String hash = encoder.encodePassword(agent.getUserAccount().getPassword(), null);
			agent.getUserAccount().setPassword(hash);
		}

		if (agent.getId() == 0) {
			agent.setFolders(new ArrayList<Folder>());
			final Collection<Folder> folders = this.folderService.defaultFolders();
			agent.getFolders().addAll(folders);
		}

		final List<Authority> authorities = new ArrayList<Authority>();
		final Authority auth = new Authority();
		auth.setAuthority(Authority.USER);
		authorities.add(auth);
		agent.getUserAccount().setAuthorities(authorities);
		final UserAccount ua = this.userAccountService.save(agent.getUserAccount());
		agent.setUserAccount(ua);

		final Agent res = this.agentRepository.save(agent);
		return res;
	}
	
	public Agent findOne(final int agentId) {
		Assert.isTrue(agentId != 0);
		final Agent res = this.agentRepository.findOne(agentId);
		return res;
	}
	
	public Collection<Agent> findAll() {
		final Collection<Agent> res;
		res = this.agentRepository.findAll();
		return res;
	}
	
	public Agent findByAgentAccountId(final int userAccountId) {
		Assert.isTrue(userAccountId != 0);
		final Agent res = this.agentRepository.findAgentByUserAccountId(userAccountId);
		return res;
	}

	public Agent findByPrincipal() {
		final UserAccount u = LoginService.getPrincipal();
		final Agent res = this.agentRepository.findAgentByUserAccountId(u.getId());
		return res;
	}
	
}
