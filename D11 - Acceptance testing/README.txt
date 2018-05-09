-----Regarding the database-----
The name of the database used in this deliverable is "Acme-Newspaper"

-----Regarding tests executed in the pre-production configuration-----
Inside the Item 5 and 6 folders there is a database creation script with the data used in during the performance
and acceptation tests.

-----Notes on some functional requirements-----
When it comes to the creation of a volume and adding newspapers to it, we have considered that an user can only 
add to a volume newspapers that he has created and that are in final mode.
When it comes to the creation of advertisements, we have considered that an agent can only create one advertisement
per newspaper.
The price of the volumes is the total price of the newspapers that it contains.
When adding a newspaper to a volume that has users subscribed to it, users are not automatically subscribed to that volume.
The reason why we have decided to do so is because due to how the price calculation of the volume works, the user subscribed to the volume has not paid for
that newspaper.
A newspaper is considered privated if its price is greater than 0. Otherwise, it is considered a public newspaper.

-----Known issues-----
The publication date when listing chirps is only available in the format yyyy/mm/dd HH:MM because otherwise, 
the listing can not order them by their publication date properly.

When an user creates a newspaper, if he tries to add the newspaper to a volume, the list of available newspapers to add was shown incorrectly, 
some columns were not displayed. After logging out and the logging in as the same user, the issue was solved. In order to prevent this, we removed
the "keep status" from the newspaper listing

