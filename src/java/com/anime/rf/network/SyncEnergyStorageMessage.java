package com.anime.rf.network;

import java.io.IOException;

import javax.annotation.Nullable;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufInputStream;
import io.netty.handler.codec.EncoderException;
import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.nbt.NBTSizeTracker;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;

public class SyncEnergyStorageMessage implements IMessage {
	
	private int networkPosition;
	private NBTTagCompound storage;
	
	public SyncEnergyStorageMessage() { }
	
	public SyncEnergyStorageMessage(int id, NBTTagCompound storage) {
		this.networkPosition = id;
		this.storage = storage;
	}
	
	@Override
	public void fromBytes(ByteBuf buf) {
		networkPosition = ByteBufUtils.readVarInt(buf, 2);
		try {
			storage = readNBTTagCompoundFromByteBuf(buf);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void toBytes(ByteBuf buf) {
		ByteBufUtils.writeVarInt(buf, networkPosition, 2);
		ByteBufUtils.writeTag(buf, storage);
	}
	
	@Nullable
    private NBTTagCompound readNBTTagCompoundFromByteBuf(ByteBuf buf) throws IOException {
        int i = buf.readerIndex();
        byte b0 = buf.readByte();

        if (b0 == 0) {
            return null;
        }
        else {
            buf.readerIndex(i);

            try {
                return CompressedStreamTools.read(new ByteBufInputStream(buf), new NBTSizeTracker(52428800L));
            }
            catch (IOException ioexception) {
                throw new EncoderException(ioexception);
            }
        }
    }
	
	public int getNetworkID() {
		return networkPosition;
	}
	
	public NBTTagCompound getStorage() {
		return storage;
	}
	
}
