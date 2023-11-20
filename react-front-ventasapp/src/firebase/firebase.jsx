
import { initializeApp } from "firebase/app";
import { getStorage, ref , uploadBytes , getDownloadURL , deleteObject } from "firebase/storage";

const firebaseConfig = {
   // your config object
   // you get from firebase
   apiKey: "AIzaSyDL_Fgg_eAY3HJLnZQtKzUyQ19yEr8HLnE",
   authDomain: "proyecto5-29fe9.firebaseapp.com",
   projectId: "proyecto5-29fe9",
   storageBucket: "proyecto5-29fe9.appspot.com",
   messagingSenderId: "994522978187",
   appId: "1:994522978187:web:928aba4be2ba6d11cc418e",
   measurementId: "G-LHZ350GXY7"
};


    async function saveFile(imagetoUpload){

        if(imagetoUpload == null) return ;

        const app = initializeApp(firebaseConfig);
        const storage = getStorage(app);
        let imageRef = ref(storage, `producto/${imagetoUpload.name}`);

         let snapshot = await uploadBytes(imageRef, imagetoUpload) ;
        
         imageRef = ref(storage, snapshot.metadata.fullPath);
         let url = await getDownloadURL(imageRef);
         return  url ;
    };


  async function getFile(){

     const app = initializeApp(firebaseConfig);
     const storage = getStorage(app);
     const imageRef = ref(storage, `producto/img1.jpg`);

     let url = await getDownloadURL(imageRef);
     return url;
  };


async function deleteFile(imagePath){

 const app = initializeApp(firebaseConfig);
 const storage = getStorage(app);
 const imageRef = ref(storage, imagePath);

   deleteObject(imageRef).then(() => {
      // File deleted successfully
   }).catch((error) => {
   // Uh-oh, an error occurred!
   });
  };

export {saveFile , getFile , deleteFile};