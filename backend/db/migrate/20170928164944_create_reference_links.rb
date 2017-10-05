class CreateReferenceLinks < ActiveRecord::Migration[5.1]
  def change
    create_table :reference_links do |t|
      t.string :name
      t.string :url
      t.string :logo

      t.timestamps
    end
  end
end
