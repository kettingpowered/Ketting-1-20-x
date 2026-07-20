package org.kettingpowered.ketting.remapper;

import net.md_5.specialsource.repo.ClassRepo;
import org.objectweb.asm.tree.ClassNode;

/*
 * Allow passing config through ClassRepo
 */
public record ClassRepoWrapper(ClassRepo inner, KettingRemapConfig config) implements ClassRepo {
    @Override
    public ClassNode findClass(String internalName) {
        return inner.findClass(internalName);
    }

}
