//---------------------------------------------//
//   Esercizio Sistemi distribuiti 14/10/2019  //
//              client_ABCD.c                  //
//            Nicolas Nucifora                 //
//---------------------------------------------//
#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <string.h>
#include <sys/socket.h>
#include <sys/types.h>
#include <netdb.h>
#include <netinet/in.h>
#include <arpa/inet.h>

#define SERVPORT 9999
#define MAXBUF 1024

int main(int argc, char** argv){
    if(argc != 2){
        printf("Usage: server-address\n");
        exit(1);
    }

    int sock, retcode;
    struct sockaddr_in server;
    char msg[MAXBUF];
    size_t msglen;

    if((sock = socket(AF_INET, SOCK_STREAM, 0)) < 0){
        perror("socket()\n");
        exit(-1);
    }

    memset(&server, 0, sizeof(server));
    server.sin_family = AF_INET;
    server.sin_port = htons(SERVPORT);
    server.sin_addr.s_addr = inet_addr(argv[1]);

    if((connect(sock, (struct sockaddr *)&server, sizeof(server))) < 0){
        perror("connect()\n");
        exit(-1);
    }

    printf("---Connection established. Quit-> CTRL-C\n\n");

    while((fgets(msg, 1024, stdin)) > 0){
        fflush(stdout);
        if((retcode = write(sock, msg, strlen(msg))) < 0){
            perror("write()\n");
            exit(-1);
        }
        
        printf("---sent %d bytes---\n", retcode);

        if((retcode = read(sock, msg, MAXBUF-1)) < 0){
            printf("read()\n");
            exit(-1);
        }

        printf("---received %d bytes---\n\nmsg:\t%s\n\n", retcode, msg);
        memset(msg, 0, sizeof(msg));

    }
    
    close(sock);
}