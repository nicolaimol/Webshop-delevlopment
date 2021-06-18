FROM golang

WORKDIR /go/src/app

RUN go mod init

COPY . .

RUN go get -u github.com/gorilla/mux
RUN go get -u github.com/go-sql-driver/mysql


CMD go build && ./app