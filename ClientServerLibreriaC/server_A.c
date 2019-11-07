//-----------------------------------------------//
//  Esercizio Sistemi distribuiti 14/10/2019     //
//              Server_A.c                       //
//           Nicolas Nucifora                    //
//-----------------------------------------------//

#include <stdlib.h>
#include <stdio.h>
#include <unistd.h>
#include <sys/types.h>
#include <sys/socket.h>
#include <arpa/inet.h>
#include <netinet/in.h>
#include <netdb.h>
#include <string.h>

#define LOCALPORT 9999
#define MAXBUFF 1024

int main(int argc, char** argv){

    int welcomeSock, connectionSock, retcode;
    struct sockaddr_in server, client;
    socklen_t clientLen;
    char msg[MAXBUFF];

    if((welcomeSock = socket(AF_INET, SOCK_STREAM, 0)) < 0){
        perror("socket()\n");
        exit(-1);
    }

    memset(&server, 0, sizeof(server));
    server.sin_family = AF_INET;
    server.sin_addr.s_addr = INADDR_ANY;
    server.sin_port = htons(LOCALPORT);

    if((bind(welcomeSock, (struct sockaddr *)&server, sizeof(server))) < 0){
        perror("bind()\n");
        exit(-1);
    }

    printf("...Server is listening...\n");

    listen(welcomeSock, 2);

    clientLen = sizeof(client);

    while(1){
        if((connectionSock = accept(welcomeSock, (struct sockaddr *)&client, &clientLen)) < 0){
            perror("accept()\n");
            exit(-1);
        }

        printf("Connection established with client(%s) on socket %d\n", inet_ntoa(client.sin_addr), LOCALPORT);

        while((retcode = read(connectionSock, msg, MAXBUFF-1)) > 0){
            msg[retcode] = '\0';
            printf("Message from %s: %s\n", inet_ntoa(client.sin_addr), msg);
        }
    }
}