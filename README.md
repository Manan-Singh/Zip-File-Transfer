# Zip-File-Transfer
A sever/client program that can transfer a zip file filled with other files over a network. It also contains functionality to create brand new zip files by using the Zip4j API. Here's how it works:

If operating as a server, then the user must first select a file by pressing the "create a new zip file" button and following the steps from there. After the zip file is selected, the user can then host the file transfer on a port on his computer. The rest is left up to the client.

The client operator is much easier to use. The user simply presses the "recieve a file" button on the home screen. Then he just enters the port on the other computer where the server is being hosted on as well as the server computer's host-name.
