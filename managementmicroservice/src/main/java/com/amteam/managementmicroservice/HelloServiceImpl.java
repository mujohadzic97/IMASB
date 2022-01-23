package com.amteam.managementmicroservice;

import com.example.hello.HelloRequest;
import com.example.hello.HelloResponse;
import com.example.hello.HelloServiceGrpc;
import io.grpc.stub.StreamObserver;
import org.springframework.stereotype.Service;

@Service
public class HelloServiceImpl extends HelloServiceGrpc.HelloServiceImplBase implements HelloService {
    @Override
    public void hello(HelloRequest request, StreamObserver<HelloResponse> responseObserver) {
        String greeting = "Hello, " + request.getFirstName() + " " + request.getLastName();
        HelloResponse response = HelloResponse.newBuilder()
                .setGreeting(greeting)
                .build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
}
