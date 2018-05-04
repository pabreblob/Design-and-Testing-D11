
package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.SafeHtml;
import org.hibernate.validator.constraints.SafeHtml.WhiteListType;
import org.hibernate.validator.constraints.URL;

@Entity
@Access(AccessType.PROPERTY)
@Table(indexes = {
	@Index(columnList = "owner_id, newspaper_id"), @Index(columnList = "marked")
})
public class Advertisement extends DomainEntity {

	private String		title;
	private String		bannerUrl;
	private String		pageUrl;
	private boolean		marked;

	private CreditCard	creditCard;
	private Newspaper	newspaper;
	private Agent		owner;


	public Advertisement() {
		super();
	}

	@NotBlank
	@SafeHtml(whitelistType = WhiteListType.NONE)
	public String getTitle() {
		return this.title;
	}

	public void setTitle(final String title) {
		this.title = title;
	}

	@NotBlank
	@URL
	@SafeHtml(whitelistType = WhiteListType.NONE)
	public String getBannerUrl() {
		return this.bannerUrl;
	}

	public void setBannerUrl(final String bannerUrl) {
		this.bannerUrl = bannerUrl;
	}

	@NotBlank
	@URL
	@SafeHtml(whitelistType = WhiteListType.NONE)
	public String getPageUrl() {
		return this.pageUrl;
	}

	public void setPageUrl(final String pageUrl) {
		this.pageUrl = pageUrl;
	}

	public boolean isMarked() {
		return this.marked;
	}

	public void setMarked(final boolean marked) {
		this.marked = marked;
	}

	@Valid
	@NotNull
	public CreditCard getCreditCard() {
		return this.creditCard;
	}

	public void setCreditCard(final CreditCard creditCard) {
		this.creditCard = creditCard;
	}

	@Valid
	@NotNull
	@ManyToOne(optional = false)
	public Newspaper getNewspaper() {
		return this.newspaper;
	}

	public void setNewspaper(final Newspaper newspaper) {
		this.newspaper = newspaper;
	}

	@Valid
	@NotNull
	@ManyToOne(optional = false)
	public Agent getOwner() {
		return this.owner;
	}

	public void setOwner(final Agent owner) {
		this.owner = owner;
	}

}
