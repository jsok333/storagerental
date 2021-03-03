package storageassign;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import storageassign.config.kafka.KafkaProcessor;
import storageassign.util.Assigner;

@Service
public class StorageassignPolicyHandler {
	@Autowired
    StorageassignRepository 할당Repository;
    @StreamListener(KafkaProcessor.INPUT)
    public void onStringEventListener(@Payload String eventString){

    }
    
    //private String 호출상태; //호출,호출중,호출확정,호출취소
    @StreamListener(KafkaProcessor.INPUT)
    public void whenever택시할당요청됨_(@Payload StoragemanageAssigned 택시할당요청됨){
    	System.out.println("##### EVT TYPE[StoragemanageAssigned]  : " + 택시할당요청됨.getEventType());
        if(택시할당요청됨.isMe()){
            System.out.println("##### listener  : " + 택시할당요청됨.toJson());
            
            if(택시할당요청됨.get호출상태() != null  && 택시할당요청됨.get호출상태().equals("호출중"))
            {
            	
            	택시할당요청됨.set호출상태("호출확정");
            	//StorageassignCompleted StorageassignCompleted = Assigner.get택시할당됨();
            	//BeanUtils.copyProperties(StoragemanageAssigned, StorageassignCompleted);
            	//StorageassignCompleted.setEventType("StorageassignCompleted");
            	택시할당요청됨.publish();
            	
            	StorageassignCompleted 할당확인됨 = Assigner.get택시할당됨();
            	할당확인됨.setId(택시할당요청됨.getId());
            	할당확인됨.set할당상태("할당확정");
                할당확인됨.set고객휴대폰번호(택시할당요청됨.get고객휴대폰번호());
                할당확인됨.set호출위치(택시할당요청됨.get고객위치());
            	할당확인됨.setEventType("StorageassignCompleted");
            	//StoragemanageAssigned.publishAfterCommit();
            	할당확인됨.publish(); 
            }  
        }
    }
    
    @StreamListener(KafkaProcessor.INPUT)
    public void whenever할당확인됨_(@Payload StorageassignCompleted 할당확인됨){
    	System.out.println("##### EVT TYPE[StorageassignCompleted]  : " + 할당확인됨.getEventType());
        if(할당확인됨.isMe()){
            System.out.println("##### listener  : " + 할당확인됨.toJson());
            
            if(할당확인됨.get할당상태() != null  && 할당확인됨.get할당상태().equals("할당확정"))
            {
            	
//            	StorageassignCompleted StorageassignCompleted = Assigner.get택시할당됨();
//            	BeanUtils.copyProperties(StoragemanageAssigned, StorageassignCompleted);
//            	
//                //StorageassignCompleted.setEventType("StorageassignCompleted");
//            	StorageassignCompleted.setEventType("StorageassignCompleted");
//            	//StoragemanageAssigned.publishAfterCommit();
//            	StorageassignCompleted.publish();
            }  
        }
    }
    
    
    @StreamListener(KafkaProcessor.INPUT)
    public void whenever택시할당취소됨_(@Payload StoragemanageCancelled 택시할당취소됨){
    	
        if(택시할당취소됨.isMe()){
            System.out.println("##### listener  : " + 택시할당취소됨.toJson());
            
            
            할당Repository.findById(Long.valueOf(택시할당취소됨.getId())).ifPresent((택시호출) -> {
				택시호출.setStatus("할당취소");
				할당Repository.save(택시호출);
			});
            
            택시할당취소됨.publish();
        }
    }

}
