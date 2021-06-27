package main

import (
	"encoding/json"
	"fmt"
	"net/http"
)

type loginStruct struct {
	Username string `json:"username"`
	Password string `json:"password"`
	ID string `json:"id"`
}

func login(w http.ResponseWriter, r *http.Request) {
	w.Header().Set("Content-Type", "application/json")
	var loginObj loginStruct
	json.NewDecoder(r.Body).Decode(&loginObj)

	var logedIn loginStruct
	//fmt.Println(loginObj)

	err := db.QueryRow("SELECT username, password, id FROM user where username = ?", loginObj.Username).Scan(&logedIn.Username, &logedIn.Password, &logedIn.ID)

	if err != nil {
		fmt.Println("error in login")
		fmt.Println(err)
		//json.NewEncoder(w).Encode("No user found")
		http.Error(w, "No user found", http.StatusBadRequest)

	} else {
		if loginObj.Password == logedIn.Password {

			db.Query("update user set id = uuid(), last = date_add(now(), interval 30 minute) where id = ?", logedIn.ID)
			db.QueryRow("select id from user where username = ?", loginObj.Username).Scan(&logedIn.ID)

			json.NewEncoder(w).Encode(logedIn.ID)
		} else {
			http.Error(w, "Wrong password", http.StatusBadRequest)
		}
	}


}

func  logout(w http.ResponseWriter, r *http.Request)  {

}
