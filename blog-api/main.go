package main

import (
	"database/sql"
	"encoding/json"
	"fmt"
	_ "github.com/go-sql-driver/mysql"
	"github.com/gorilla/mux"
	"log"
	"net/http"
	"strconv"
)

// BlogPost struct (Model)
type BlogPost struct {
	ID	string `json:"id"`
	Title string `json:"title"`
	Text string `json:"text"`
	Date string `json:"date"`
}

// get all books
func getBlogs(w http.ResponseWriter, r *http.Request) {
	w.Header().Set("Content-Type", "application/json")

	result, err := db.Query("select * from blog")

	var blogs []BlogPost

	if err != nil {
		fmt.Println("Error in get blogs")
	} else {

		for result.Next() {
			var blog BlogPost

			err = result.Scan(&blog.ID, &blog.Title, &blog.Text, &blog.Date)

			if err != nil {
				fmt.Println("Error in get blogs")
			} else {
				blogs = append(blogs, blog)
			}
		}

		json.NewEncoder(w).Encode(blogs)

	}

}

// get one book
func getBlog(w http.ResponseWriter, r *http.Request) {
	w.Header().Set("Content-Type", "application/json")
	params := mux.Vars(r)
	// loop through books and find with id

	var blog BlogPost

	err := db.QueryRow("select * from blog where id = ?", params["id"]).Scan(&blog.ID, &blog.Title, &blog.Text)
	if err != nil {
		fmt.Println("error in get blog")
		json.NewEncoder(w).Encode(&BlogPost{})
	} else {
		json.NewEncoder(w).Encode(blog)
	}


}

// create book
func createBlog(w http.ResponseWriter, r *http.Request) {
	w.Header().Set("Content-Type", "application/json")
	w.Header().Set("Access-Control-Allow-Headers", "*")
	var blog BlogPost
	json.NewDecoder(r.Body).Decode(&blog)

	var id string

	uuid := r.Header.Get("Auth")
	if uuid == "" {
		http.Error(w, "auth error", http.StatusForbidden)
	} else {
		err := db.QueryRow("select id from user where id = ? && last > now()", uuid).Scan(&id)
		if err != nil {
			http.Error(w, "Auth over", http.StatusForbidden)
		} else {
			db.Query("update user set last = date_add(now(), interval 30 minute )")

			insert, err := db.Exec("insert into blog (title, text, date) values (?, ?, CURDATE())", blog.Title, blog.Text)

			if err != nil {
				fmt.Println("Error in insert")
				json.NewEncoder(w).Encode("Error")
			} else {
				res, _ := insert.LastInsertId()
				blog.ID= strconv.FormatInt(res, 10)
				json.NewEncoder(w).Encode(blog)
			}
		}
	}


}

// update book
func updateBlog(w http.ResponseWriter, r *http.Request) {

}

// delete book
func deleteBlog(w http.ResponseWriter, r *http.Request) {

}

// init books slice

var books []BlogPost

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

var db *sql.DB = nil
var err error = nil


func main() {
	// init router
	r := mux.NewRouter()

	db, err = sql.Open("mysql", "root:password@tcp(127.0.0.1:8001)/blog")
	if err != nil {
		fmt.Println("error in db")
	}

	r.Use(accessControlMiddleware)

	// route handlers
	r.HandleFunc("/api/blog", getBlogs).Methods("GET")
	r.HandleFunc("/api/blog/{id}", getBlog).Methods("GET")
	r.HandleFunc("/api/blog", createBlog).Methods("POST", "OPTIONS")
	r.HandleFunc("/api/blog/{id}", updateBlog).Methods("PUT")
	r.HandleFunc("/api/blog/{id}", deleteBlog).Methods("DELETE")

	r.HandleFunc("/api/user/login", login).Methods("POST")
	r.HandleFunc("/api/user/logout", logout).Methods("GET")

	defer db.Close()

	/*
	headersOk := handlers.AllowedHeaders([]string{"X-Requested-With"})
	originsOk := handlers.AllowedOrigins([]string{"*"})
	methodsOk := handlers.AllowedMethods([]string{"GET", "HEAD", http.MethodPost, "PUT", "OPTIONS"})

	 */

	log.Fatal(http.ListenAndServe(":8000", r))

}

func accessControlMiddleware(next http.Handler) http.Handler {
	return http.HandlerFunc(func(w http.ResponseWriter, r *http.Request) {
		w.Header().Set("Access-Control-Allow-Origin", "*")
		w.Header().Set("Access-Control-Allow-Methods", "GET, POST, OPTIONS, PUT")
		w.Header().Set("Access-Control-Allow-Headers", "Origin, Content-Type, Auth")
		w.Header().Set("Access-Control-Allow-Credentials", "true")

		//fmt.Println(r.Method)

		if r.Method == "OPTIONS" {
			return
		}

		next.ServeHTTP(w, r)
	})
}
