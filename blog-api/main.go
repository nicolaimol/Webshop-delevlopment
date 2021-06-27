package main

import (
	"database/sql"
	"fmt"
	_ "github.com/go-sql-driver/mysql"
	"github.com/gorilla/mux"
	"log"
	"net/http"
)


// init books slice

var books []BlogPost

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
