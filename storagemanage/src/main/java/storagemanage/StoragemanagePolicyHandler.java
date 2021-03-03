package storagemanage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import storagemanage.config.kafka.KafkaProcessor;

@Service
public class StoragemanagePolicyHandler {
	@Autowired
	StoragemanageRepository 택시관리Repository;

	@StreamListener(KafkaProcessor.INPUT)
	public void onStringEventListener(@Payload String eventString) {

	}

	@StreamListener(KafkaProcessor.INPUT)
	public void whenever호출취소됨_(@Payload StoragecallCancelled 호출취소됨) {
		System.out.println("##### EVT TYPE[StoragecallCancelled]  : " + 호출취소됨.getEventType());
		if (호출취소됨.isMe()) {
			System.out.println("##### listener  : " + 호출취소됨.toJson());

			if (호출취소됨.getId() != null)
				// Correlation id 는 'custel' 임
				택시관리Repository.findById(Long.valueOf(호출취소됨.getId())).ifPresent((택시관리) -> {
					택시관리.setState("호출요청취소됨");
					택시관리Repository.save(택시관리);
				});
		}
	}

	@StreamListener(KafkaProcessor.INPUT)
	public void whenever택시할당요청됨_(@Payload StoragemanageAssigned 택시할당요청됨) {
		System.out.println("##### EVT TYPE[StoragemanageAssigned]  : " + 택시할당요청됨.getEventType());
		if (택시할당요청됨.isMe()) {
			System.out.println("##### listener[StorageassignCompleted]  : " + 택시할당요청됨.toJson());

			if (택시할당요청됨.getId() != null)
				// Correlation id 는 'custel' 임
				택시관리Repository.findById(Long.valueOf(택시할당요청됨.getId())).ifPresent((택시관리) -> {
					택시관리.setState(택시할당요청됨.get호출상태());
					택시관리Repository.save(택시관리);
				});

//        	StoragemanageRepository.findBycustel(StoragemanageAssigned.getCustel()).ifPresent((Storagemanage) -> {
//				System.out.println("StoragemanageAssigned = " + Storagemanage.getCustel());
//				Storagemanage.setState(StoragemanageAssigned.getState());
//				StoragemanageRepository.save(Storagemanage);
//			});
//            Storagemanage 관리 = new Storagemanage();
//            관리.setState(StorageassignCompleted.getState());
//            관리.setAddress(StorageassignCompleted.getAddress());
//            관리.setFloor(StorageassignCompleted.getFloor());
//            관리.setStorageid(StorageassignCompleted.getStorageid());
//            StoragemanageRepository.save(관리);
		}
	}

//    @StreamListener(KafkaProcessor.INPUT)
//    public void whenever택시할당확인됨_(@Payload StorageassignCompleted StorageassignCompleted){
//    	System.out.println("##### EVT TYPE[StorageassignCompleted]  : " + StorageassignCompleted.getEventType());
//        if(StorageassignCompleted.isMe()){
//            System.out.println("##### listener  : " + StorageassignCompleted.toJson());
//            Storagemanage 관리 = new Storagemanage();
//            관리.setState(StorageassignCompleted.get할당상태());
//            관리.setAddress(StorageassignCompleted.getAddress());
//            관리.setFloor(StorageassignCompleted.getFloor());
//            관리.setStorageid(StorageassignCompleted.getStorageid());
//            StoragemanageRepository.save(관리);
//        }
//    }

}
