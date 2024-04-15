Don't Wreck my Home plan

-Start with models and data
-models should be Lease, Guest, and Host
-Lease needs getters and setters, guest and host only need getters. Setters are used with stretch goals. Include anyways for redundancy plus it's simple.
-need a way to get leases by filename and to create one with a given name. Same way it's done in Sustainable Foraging with dates should work
-no enums needed in models or data from what I can figure or see
-DataException needed, obviously.
-FindByHost method in the Lease repository instead of FindByDate
-LeaseRepository methods: FindByHost, Add, Update, getFilepath, writeAll, Serialize, Deserialize
-HostRepository methods: FindAll, FindByEmail, Serialize, Deserialize, maybe more depending on what's in the file.
-GuestRepository methods: Should(?) be the same as hostrepository, but would need to look at what's in the file that we need.
-Interfaces for all three

-Domain
-Use the same Response and Result as in Foraging in case I want to go for stretch goals later. If not, it's not hurting anything.
-LeaseService can use FindByEmail and use it for both finding hosts and call it again for finding guests
-Add method to add a lease
-validate to make sure all of the dates line up and aren't overlapping, in the past, End before Start, no nulls, the host and guest exsist
-also need update and remove for leases. update can call the same validate methods, but also needs to only be able to update dates in the future. same for delete.
-use guest email and host email to find that.
-always sort the results by date. Did that in the strings exercise, do it again here.
-calculate cost here, make a method for it
-No adding or removing guests or hosts in MVP, so for that only method should really be FindByEmail, a filter to return should work perfectly fine
-leaseID

-UI
-app is going to be really, really simple. try not to use XML, but if things are breaking go back to it.
-MainMenuOption enum
-make a consoleIO to make sure input dates and values are correct and not null. Check the example video to see exactly what inputs you'll need. At least dates and strings will be needed.
-Controller: Exit, View Reservations by Host, make a reservation, edit a reservation, cancel a reservation. run, runAppLoop, viewByEmail, getLease, getHost, getGuest
-View: selectMenuOption, getEmail (use for host and guest), addLease, confirmation check, displayHeader, displayException, displayStatus

-Tests
-generate and make as you go along. tests and doubles for all the repositories, then just tests for the domains. Probably just for Lease/Reservation unless going for stretch goals.

-Other
-make a checklist for what to do through this
-make a directory list for what it should look like at the end (it's called something specific, in the example video on slack)
-something else I'm forgetting, check slack video

might be forgetting stuff, rewatch slack video