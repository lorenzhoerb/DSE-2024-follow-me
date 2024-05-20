import { VehicleType } from '@/types'
import type { BasicVehicleInfo } from '@/types'

export const vehicles: BasicVehicleInfo[] = [
  {
    vin: 'MED-KA-2001',
    isInFollowMeMode: true,
    manufacturer: 'Mercedes',
    model: 'KA1',
    type: VehicleType.FV
  },
  {
    vin: 'XYZ-AB-1234',
    isInFollowMeMode: false,
    manufacturer: 'Toyota',
    model: 'Camry',
    type: VehicleType.LV
  },
  {
    vin: 'ABC-CD-5678',
    isInFollowMeMode: true,
    manufacturer: 'Ford',
    model: 'F-150',
    type: VehicleType.FV
  },
  {
    vin: 'DEF-GH-91011',
    isInFollowMeMode: false,
    manufacturer: 'Honda',
    model: 'Civic',
    type: VehicleType.LV
  },
  {
    vin: 'JKL-MN-121314',
    isInFollowMeMode: true,
    manufacturer: 'Chevrolet',
    model: 'Silverado',
    type: VehicleType.FV
  },
  {
    vin: 'OPQ-RS-151617',
    isInFollowMeMode: false,
    manufacturer: 'Volkswagen',
    model: 'Golf',
    type: VehicleType.LV
  },
  {
    vin: 'TUV-WX-181920',
    isInFollowMeMode: true,
    manufacturer: 'BMW',
    model: '3 Series',
    type: VehicleType.FV
  },
  {
    vin: 'YZA-BC-212223',
    isInFollowMeMode: false,
    manufacturer: 'Tesla',
    model: 'Model S',
    type: VehicleType.LV
  },
  {
    vin: 'CDE-FG-242526',
    isInFollowMeMode: true,
    manufacturer: 'Audi',
    model: 'A4',
    type: VehicleType.FV
  },
  {
    vin: 'HIJ-KL-272829',
    isInFollowMeMode: false,
    manufacturer: 'Subaru',
    model: 'Outback',
    type: VehicleType.LV
  },
  {
    vin: 'MNO-PQ-303132',
    isInFollowMeMode: true,
    manufacturer: 'Jeep',
    model: 'Wrangler',
    type: VehicleType.FV
  },
  {
    vin: 'RST-UV-333435',
    isInFollowMeMode: false,
    manufacturer: 'Hyundai',
    model: 'Elantra',
    type: VehicleType.LV
  },
  {
    vin: 'WXY-ZA-363738',
    isInFollowMeMode: true,
    manufacturer: 'Kia',
    model: 'Sorento',
    type: VehicleType.FV
  },
  {
    vin: 'BCD-EF-394041',
    isInFollowMeMode: false,
    manufacturer: 'Lexus',
    model: 'RX',
    type: VehicleType.LV
  },
  {
    vin: 'GHI-JK-424344',
    isInFollowMeMode: true,
    manufacturer: 'Mazda',
    model: 'CX-5',
    type: VehicleType.FV
  },
  {
    vin: 'LMN-OP-454647',
    isInFollowMeMode: false,
    manufacturer: 'Buick',
    model: 'Enclave',
    type: VehicleType.LV
  },
  {
    vin: 'QRS-TU-484950',
    isInFollowMeMode: true,
    manufacturer: 'Volvo',
    model: 'XC90',
    type: VehicleType.FV
  },
  {
    vin: 'VWX-YZ-515253',
    isInFollowMeMode: false,
    manufacturer: 'Porsche',
    model: '911',
    type: VehicleType.LV
  },
  {
    vin: 'ZA1-BC2-545556',
    isInFollowMeMode: true,
    manufacturer: 'Infiniti',
    model: 'Q50',
    type: VehicleType.FV
  }
]

export const logs = [
  {
    timestamp: new Date('2024-05-07T08:00:00Z'),
    message: 'User login successful'
  },
  {
    timestamp: new Date('2024-05-06T12:30:00Z'),
    message: 'Database connection established'
  },
  {
    timestamp: new Date('2024-05-05T16:45:00Z'),
    message: 'Error: Unable to process request'
  },
  {
    timestamp: new Date('2024-05-04T10:15:00Z'),
    message: 'File upload completed'
  },
  {
    timestamp: new Date('2024-05-03T14:20:00Z'),
    message: 'Server rebooted successfully'
  },
  {
    timestamp: new Date('2024-05-02T09:45:00Z'),
    message: 'New user registered'
  },
  {
    timestamp: new Date('2024-05-01T17:30:00Z'),
    message: 'Payment received'
  },
  {
    timestamp: new Date('2024-04-30T11:00:00Z'),
    message: 'Security patch applied'
  },
  {
    timestamp: new Date('2024-04-29T13:55:00Z'),
    message: 'Backup completed'
  },
  {
    timestamp: new Date('2024-04-28T08:40:00Z'),
    message: 'Website traffic increased by 20%'
  },
  {
    timestamp: new Date('2024-04-27T15:10:00Z'),
    message: 'Email sent to all subscribers'
  },
  {
    timestamp: new Date('2024-04-26T09:25:00Z'),
    message: 'Critical error: Server overload'
  },
  {
    timestamp: new Date('2024-04-25T11:20:00Z'),
    message: 'Task completed: Project milestone reached'
  },
  {
    timestamp: new Date('2024-04-24T14:50:00Z'),
    message: 'API endpoint upgraded'
  },
  {
    timestamp: new Date('2024-04-23T16:35:00Z'),
    message: 'Data synchronization in progress'
  },
  {
    timestamp: new Date('2024-04-22T10:05:00Z'),
    message: 'Database backup initiated'
  },
  {
    timestamp: new Date('2024-04-21T12:45:00Z'),
    message: 'Warning: Memory usage high'
  },
  {
    timestamp: new Date('2024-04-20T09:15:00Z'),
    message: 'New feature deployed'
  },
  {
    timestamp: new Date('2024-04-19T14:30:00Z'),
    message: 'System update available'
  },
  {
    timestamp: new Date('2024-04-18T08:55:00Z'),
    message: 'API rate limit reached'
  }
]
