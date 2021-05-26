HTTPS:
1. Hyper Text Transfer Protocol -Secure
2. Extension of HTTP protocol, which is an application layer protocol
3. It is encrypted with TLS(Transport Layer Security) and formerly SSL(Secure Socket Layer).
4. It's based certificate authorization
5. A public password can be created only by domain owners. So, it's secure
6. If I am <b>encrypting</b> a message with my <b>public key</b>, it can be <b>decrypted</b> only by my <b>private key</b>
7. If <b>somebody has my public key</b>, they can <b>verify my signature</b> and ensure that it's send from me or somebody having my peivate key access.


### Consider our gmail id as public key and password to gmail account is private key.
### Anyone who know my mail id can send me a message as they are holding my public key.
### I can read that message by login into my account with the help of my password, which is my private key.
### Ie; in HTTPS, anyone knowing the public key can send messages. Only if the receiver has private key, can decrypt and read it.
### If I am sending a mail from my inbox with the help of private key(password), people who knows my public key (mail id) can verify the mail and say that I am a trusted source.
### Otherwise they can mark me us spam mail etc.
### This is how a message send encryting with private key can be verified by those having public keys.



#### Public keys are issues by trusted authorities.
#### There are self signed certificates as well.
#### All browsers will be having a known set of CA and their public keys with them.
#### If CA confirms that the public key is valid one, browser will proceed.
#### In some cases, the certificates can be from other CA which are not there in trusted list kept by our browsers. That time, they will prompt us to trust and add the certificate.
#### Once they got confirmation that the CA public key is valid,browser creates a secret using this public key and encrypts and sends it server/ sender.
#### They are the only person with private key.
#### They will be able to decrypt that message and now they got the secret key generated and send by browser for further communication.
#### Now, with the help of this new secret, both server and browser starts sending encryted messages.
#### Other than both of them, no body else will be knowing the new secret.


### Why we need HTTPS?
  * Its for secure communication.
  * By SSL or now TLS.
  * For a normal user, to send payment details, credentials etc in an encryted way, it helps.
  * For websites, it protects from intruders posting some adds etc without permissions.
  * They won't get secrets of user or the website as all communication will be encryted way.



         browser                                                                                 Youtube
          |
          |----------------------------------- www.youtube.com --------------------------------------->|
          |                                                                                            |
          |<--------------Hey this is my cerificate from Google CA holding my public key---------------|
          |                  you check and let me know if you trust me                                 |
          |                                                                                            |
          |-----------------I know Google CA and verified ur public key and its valid----------------->|
          |                     I think I can trust you now.                                           |
          |   By the way,I have created a secret with ur public key and sending it to you.             |
          |   Try to decrypt it and let me know if you get my secret                                   |
          |<-----------------Yeah...I can do that as I have private key to decrypt ur message----------|
          |                  I got your secret as well.                                                |          [If some intruder gets the secret encrypted with
          |                 Let's start communicating by encryting with this scecret now               |           public key, they cannot decryt it.]
          |                                                                                            |          [If intruder tries to chnage secret, youtube will
          |                           <-------------------------->                                     |           not be able to decrypt it with private key.
          |                               encrypted messages                                           |          [If it was decrypted, and started sending message 
          |                                                                                            |           to browser, it willnot be able to read it with 
                                                                                                                    altered secret.]
