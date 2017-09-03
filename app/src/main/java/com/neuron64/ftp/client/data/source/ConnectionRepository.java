//package com.neuron64.ftp.client.data.source;
//
//import android.support.annotation.Nullable;
//
//import com.neuron64.ftp.client.data.model.Connection;
//
//import java.util.List;
//
//import io.reactivex.Observable;
//import io.reactivex.Single;
//
///**
// * Created by Neuron on 03.09.2017.
// */
//
//public class ConnectionRepository implements ConnectionDataSource{
//
//    @Nullable
//    private static ConnectionRepository INSTANCE = null;
//
//    public ConnectionRepository() {
//        if(INSTANCE == null){
//            ConnectionRepository.INSTANCE = new ConnectionRepository();
//        }
//    }
//
//    @Override
//    public Observable<List<Connection>> getAllConnections() {
//        return null;
//    }
//
//    @Override
//    public Single<Connection> getConnectionById(int id) {
//        return null;
//    }
//
//    @Override
//    public void saveConnection(Connection connection) {
//
//    }
//
//    @Override
//    public void deleteConnection(Connection connection) {
//
//    }
//
//    @Override
//    public void updateConnection(Connection connection) {
//
//    }
//}
