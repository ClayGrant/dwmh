~~-models
 -reservations, hosts, guests
 -getters and setters for all because it's easy~~
Done unless something comes up later that I need to fix

~~-data
 -fill out the internals for the different fields
 -data exception
 -add, update, getfilepath, writeall, serialize, deserialize, findbyid (use host ID)
 -findbyemail for guest and host
 -tests, repositorydoubles~~
done except for if I find problems later

~~-domain
 -findbyemail for guest and host service
   -error messages for no email
   -Validations? in case the file is wrong.
 -findbyhostID, add, update, remove, and validate fields for reservation service
 -response and result
 -tests~~
ended up doing it differently, but end result is the same. Completed unless future problems arise.

-ui
 -main menu enum
 -console io: readString, readLocalDate (I don't think there's anything else actually input in MVP)
 -controller: run, runapploop, viewReservationByHostEmail, editReservation, cancelReservation, getHost, getGuest
 -view: selectMenuOption, getEmail, addReservation, updateReservation, removeReservation;
        displayHeader, displayException, displayResult, displayStatus, displayReservations
 -app: simple, use spring. Try to not use XML.