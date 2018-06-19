package ap2.imageserviceapp;

import java.io.File;

interface ITCPCommunicator {
    void connect();
    void send(File img);
}
