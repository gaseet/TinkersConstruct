package tconstruct.tools.blocks;

import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import tconstruct.blocks.TConstructBlock;

public class MultiBrick extends TConstructBlock {

    static String[] blockTextures = { "brick_obsidian", "brick_sandstone", "brick_netherrack", "brick_stone_refined",
            "brick_iron", "brick_gold", "brick_lapis", "brick_diamond", "brick_redstone", "brick_bone", "brick_slime",
            "brick_blueslime", "brick_endstone", "brick_obsidian_ingot" };

    public MultiBrick() {
        super(Material.rock, 3f, blockTextures);
    }

    @Override
    public float getBlockHardness(World world, int x, int y, int z) {
        int meta = world.getBlockMetadata(x, y, z);
        return switch (meta) {
            case 0, 13 -> Blocks.obsidian.getBlockHardness(world, x, y, z);
            case 1 -> Blocks.sandstone.getBlockHardness(world, x, y, z);
            case 2 -> Blocks.netherrack.getBlockHardness(world, x, y, z);
            case 3 -> Blocks.stone.getBlockHardness(world, x, y, z);
            case 4 -> Blocks.iron_block.getBlockHardness(world, x, y, z);
            case 5 -> Blocks.gold_block.getBlockHardness(world, x, y, z);
            case 6 -> Blocks.lapis_block.getBlockHardness(world, x, y, z);
            case 7 -> Blocks.diamond_block.getBlockHardness(world, x, y, z);
            case 8 -> Blocks.redstone_block.getBlockHardness(world, x, y, z);
            case 9 -> 1.0F;
            case 10, 11 -> 1.5F;
            case 12 -> Blocks.end_stone.getBlockHardness(world, x, y, z);
            default -> blockHardness;
        };
    }

    @Override
    public float getExplosionResistance(Entity entity, World world, int x, int y, int z, double explosionX,
            double explosionY, double explosionZ) {
        int meta = world.getBlockMetadata(x, y, z);
        return switch (meta) {
            case 0, 13 -> Blocks.obsidian
                    .getExplosionResistance(entity, world, meta, meta, meta, explosionZ, explosionZ, explosionZ);
            case 1 -> Blocks.sandstone
                    .getExplosionResistance(entity, world, meta, meta, meta, explosionZ, explosionZ, explosionZ);
            case 2 -> Blocks.netherrack
                    .getExplosionResistance(entity, world, meta, meta, meta, explosionZ, explosionZ, explosionZ);
            case 3 -> Blocks.stone
                    .getExplosionResistance(entity, world, meta, meta, meta, explosionZ, explosionZ, explosionZ);
            case 4 -> Blocks.iron_block
                    .getExplosionResistance(entity, world, meta, meta, meta, explosionZ, explosionZ, explosionZ);
            case 5 -> Blocks.gold_block
                    .getExplosionResistance(entity, world, meta, meta, meta, explosionZ, explosionZ, explosionZ);
            case 6 -> Blocks.lapis_block
                    .getExplosionResistance(entity, world, meta, meta, meta, explosionZ, explosionZ, explosionZ);
            case 7 -> Blocks.diamond_block
                    .getExplosionResistance(entity, world, meta, meta, meta, explosionZ, explosionZ, explosionZ);
            case 8 -> Blocks.redstone_block
                    .getExplosionResistance(entity, world, meta, meta, meta, explosionZ, explosionZ, explosionZ);
            case 9 -> 1.0F;
            case 10, 11 -> 1.5F;
            case 12 -> Blocks.end_stone
                    .getExplosionResistance(entity, world, meta, meta, meta, explosionZ, explosionZ, explosionZ);
            default -> getExplosionResistance(entity, world, meta, meta, meta, explosionZ, explosionZ, explosionZ);
        };
    }

    @Override
    public void onEntityCollidedWithBlock(World world, int x, int y, int z, Entity entity) {
        int meta = world.getBlockMetadata(x, y, z);
        if (meta == 10 || meta == 11) {
            if (entity.motionY < 0) entity.motionY *= -1.2F;
            entity.fallDistance = 0;
        }
    }

    // TODO getCollisionBoundingBoxFromPool
    @Override
    public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int x, int y, int z) {
        int meta = world.getBlockMetadata(x, y, z);
        if (meta == 10 || meta == 11)
            return AxisAlignedBB.getBoundingBox(x, y, z, (double) x + 1.0D, (double) y + 0.625D, (double) z + 1.0D);
        return super.getCollisionBoundingBoxFromPool(world, x, y, z);
    }

    /*
     * @Override public int getRenderType () { return BrickRender.model; }
     */

    @Override
    public boolean canProvidePower() {
        return true;
    }

    @Override
    public boolean canConnectRedstone(IBlockAccess world, int x, int y, int z, int side) {
        return world.getBlockMetadata(x, y, z) == 8 && super.canConnectRedstone(world, x, y, z, side);
    }

    @Override
    public int isProvidingWeakPower(IBlockAccess world, int x, int y, int z, int side) {
        if (world.getBlockMetadata(x, y, z) == 8) return 4;
        return 0;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister iconRegister) {
        this.icons = new IIcon[textureNames.length];

        for (int i = 0; i < this.icons.length; ++i) {
            this.icons[i] = iconRegister.registerIcon("tinker:bricks/" + textureNames[i]);
        }
    }

    @Override
    public boolean isNormalCube(IBlockAccess world, int x, int y, int z) {
        int meta = world.getBlockMetadata(x, y, z);
        if (meta != 8) {
            return true;
        }
        return super.isNormalCube(world, x, y, z);
    }
}
