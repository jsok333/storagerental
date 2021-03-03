package storagecall;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import storagecall.config.kafka.KafkaProcessor;

@Service
public class StoragecallPolicyHandler {
	@Autowired
	StoragecallRepository 택시호출Repository;

	@StreamListener(KafkaProcessor.INPUT)
	public void onStringEventListener(@Payload String eventString) {

	}

	@StreamListener(KafkaProcessor.INPUT)
	public void whenever할당확인됨_(@Payload StorageassignCompleted 할당확인됨) {
		System.out.println("##### EVT TYPE[StorageassignCompleted]  : " + 할당확인됨.getEventType());
		if (할당확인됨.isMe() && 할당확인됨.get고객휴대폰번호() != null) {

//           try {
//               // 원래 데이터가 트랜잭션 커밋되기도 전에 이벤트가 너무 빨리 도달하는 경우를 막기 위함
//               Thread.currentThread().sleep(3000); //  no good. --> pay 가 TX 를 마친 후에만 실행되도록 수정함
//           } catch (InterruptedException e) {
//               e.printStackTrace();
//           }
			System.out.println("##### listener[StorageassignCompleted]  : " + 할당확인됨.toJson());
			

			// Correlation id 는 '고객휴대폰번호' 임
			if(할당확인됨.getId() != null)
				택시호출Repository.findById(Long.valueOf(할당확인됨.getId())).ifPresent((택시호출) -> {
					택시호출.setStatus("호출확정");
					택시호출Repository.save(택시호출);
				});
//			StoragecallRepository.findBy휴대폰번호(StorageassignCompleted.get고객휴대폰번호()).ifPresent((Storagecall) -> {
//				System.out.println("StorageassignCompleted = " + StorageassignCompleted.get고객휴대폰번호());
//				Storagecall.setStatus("호출확정");
//				StoragecallRepository.save(Storagecall);
//			});
		}

//		if (StorageassignCompleted.isMe()) {
//			Storagecall 호출 = new Storagecall();
//			호출.setStatus(StorageassignCompleted.get할당상태());
//			StoragecallRepository.save(호출);
//
//			System.out.println("##### listener[StorageassignCompleted]  : " + StorageassignCompleted.toJson());
//		}
	}

	@StreamListener(KafkaProcessor.INPUT)
	public void whenever할당취소됨_(@Payload StorageassignCancelled 할당취소됨) {
		System.out.println("##### EVT TYPE[StorageassignCancelled]  : " + 할당취소됨.getEventType());
		if (할당취소됨.isMe()) {
			System.out.println("##### listener[StorageassignCancelled]  : " + 할당취소됨.toJson());
			택시호출Repository.findById(Long.valueOf(할당취소됨.getId())).ifPresent((택시호출) -> {
				택시호출.setStatus("호출취소");
				택시호출Repository.save(택시호출);
			});
		}
	}

}
