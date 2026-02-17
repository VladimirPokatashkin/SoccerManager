package model.footballer;


import model.enums.FootballerCharacteristicsEnum;

import java.security.InvalidParameterException;
import java.util.Map;

public class BaseFootballerCharacteristics {
    private short[] characteristics = new short[FootballerCharacteristicsEnum.cnt];

	private boolean isCharacteristicValid(short characteristic) {
		return characteristic >= 0 && characteristic <= 1000;
	}

	public BaseFootballerCharacteristics(short[] characteristics) {
		this.characteristics = characteristics;
	}

	public BaseFootballerCharacteristics(Map<String, Short> characteristics)
	throws InvalidParameterException {
		characteristics.forEach((key, value) -> {
			if (!isCharacteristicValid(value)) {
				throw new InvalidParameterException("invalid characteristic: " + key + " - " + value);
			}
			int pos = FootballerCharacteristicsEnum.fromString(key).arrayPos;
			this.characteristics[pos] = value;
		});
	}

    public short characteristic(FootballerCharacteristicsEnum characteristic) {
        return this.characteristics[characteristic.arrayPos];
    }

    public void increaseCharacteristic(FootballerCharacteristicsEnum characteristic, short add) {
		short currentValue = this.characteristics[characteristic.arrayPos];
		this.characteristics[characteristic.arrayPos] = (short) Math.min(currentValue + add, 100);
    }

    public void decreaseCharacteristic(FootballerCharacteristicsEnum characteristic, short loss) {
		short currentValue = this.characteristics[characteristic.arrayPos];
		this.characteristics[characteristic.arrayPos] = (short) Math.max(currentValue - loss, 0);
    }
}