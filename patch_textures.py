import json
import os

files_to_patch = [
    r"src\main\resources\assets\vdecostreetlamp\models\block\trafficsignal.json",
    r"src\main\resources\assets\vdecostreetlamp\models\block\trafficsignal_horizontal.json"
]

for filepath in files_to_patch:
    if not os.path.exists(filepath):
        print(f"Skipping {filepath}, does not exist")
        continue

    with open(filepath, 'r') as f:
        data = json.load(f)

    if "textures" in data:
        for k, v in data["textures"].items():
            if not v.startswith("vdecostreetlamp:block/"):
                data["textures"][k] = "vdecostreetlamp:block/" + v

    with open(filepath, 'w') as f:
        json.dump(data, f, indent=4)
        print(f"Patched textures in {filepath}")
