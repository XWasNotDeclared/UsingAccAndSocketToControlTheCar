import socket
import time
from pynput.keyboard import Key, Controller
frame_count = 0
start_time = time.time()
keyboard=Controller()

#############################################
def ConnectToClient ():
    server_socket = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
    server_socket.bind(('',54504))
    server_socket.listen(1)
    print("Connect Waiting...")
    
    while(True):
        client_socket , client_address = server_socket.accept()
        #print (f"ok {client_address}")
        received_data = client_socket.recv(1024)
        data = received_data.decode()
        #print(data)
        received_values =data.split()
        if len(received_values) == 4:
            yAxis = float(received_values[0])
            zAxis = float(received_values[1])
            c = float(received_values[2])
            d = float(received_values[3])
        if (yAxis>=1):
            keyboard.press('d')
        else:
            keyboard.release('d')

        if (yAxis<=-1):
            keyboard.press('a')
        else:
            keyboard.release('a')

        if (zAxis>=2):
            keyboard.press('w')
        else:
            keyboard.release('w')

        if (zAxis<=-1):
            keyboard.press('s')
        else:
            keyboard.release('s')
        
    client_socket.close()
    server_socket.close()

if __name__ == '__main__':
    ConnectToClient()
