package com.anime.rf.network;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufInputStream;
import io.netty.handler.codec.EncoderException;
import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.nbt.NBTSizeTracker;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;

public class SyncAllNetworksMessage implements IMessage {
	
	private List<NBTTagCompound> networks = new ArrayList<NBTTagCompound>();
	
	public SyncAllNetworksMessage() {}
	
	public SyncAllNetworksMessage(List<EnergyNetwork> networks) {
		for (EnergyNetwork net : networks) {
			this.networks.add(net.writeConnectionsToNBT(new NBTTagCompound()));
		}
	}
	
	@Override
	public void fromBytes(ByteBuf buf) {
		int size = ByteBufUtils.readVarInt(buf, 5);
		for (int i = 0; i < size; i++) {
			networks.add(ByteBufUtils.readTag(buf));
		}
	}

	@Override
	public void toBytes(ByteBuf buf) {
		ByteBufUtils.writeVarInt(buf, networks.size(), 5);
		for (NBTTagCompound tag : networks) {
			ByteBufUtils.writeTag(buf, tag);
		}
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
	
	public List<NBTTagCompound> getNetworks() {
		return networks;
	}
	
}
